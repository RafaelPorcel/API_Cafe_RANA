package com.example.cafe_rana.service;


import com.example.cafe_rana.dto.CrearVentaDto;
import com.example.cafe_rana.dto.ProductoDto;
import com.example.cafe_rana.dto.VentaDto;
import com.example.cafe_rana.exceptions.ProductoNoEncontradoException;
import com.example.cafe_rana.exceptions.SinStockException;
import com.example.cafe_rana.exceptions.VentaNoEncontradaException;
import com.example.cafe_rana.model.Producto;
import com.example.cafe_rana.model.Venta;
import com.example.cafe_rana.repository.CafeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service // Le dice a Spring que esta es la capa de lógica de negocio
public class CafeService {

    @Autowired // Inyectamos el repositorio
    private CafeRepository repository;

    // =====================================
    //          MÉTODOS DE PRODUCTO
    // =====================================

    public ProductoDto registrarProducto(Producto nuevoProducto) {
        Producto producto = repository.registrarProducto(nuevoProducto);
        return productoToDto(producto);
    }

    public List<ProductoDto> consultarProductos() {
        return repository.consultarProductos().stream()
                .map(this::productoToDto) // Transformamos cada Producto en un ProductoDto
                .toList();
    }

    public void eliminarProducto(Long id) {
        repository.eliminarProducto(id);
    }

    // =====================================
    //            MÉTODOS DE VENTA
    // =====================================

    public VentaDto registrarVenta(CrearVentaDto ventaReq) {
        // 1. Agrupamos los ID para saber qué cantidad de cada producto se quiere comprar
        // Ejemplo: Si envían [1, 1, 2], obtenemos -> {1: 2 cantidades, 2: 1 cantidad}
        Map<Long, Long> conteoCantidades = ventaReq.getProductosVendidos().stream()
                .collect(Collectors.groupingBy(id -> id, Collectors.counting()));

        // 2. VALIDACIÓN PREVIA: Asegurarnos de que existen y hay stock SUFICIENTE de todo
        // Es una buena práctica validar todo antes de restar stock, para no dejar la BD en estado inconsistente.
        for (Map.Entry<Long, Long> entry : conteoCantidades.entrySet()) {
            Long idProd = entry.getKey();
            Long cantidadRequerida = entry.getValue();

            Producto p = repository.consultarProductoPorId(idProd);
            if (p == null) {
                throw new ProductoNoEncontradoException("El producto con ID " + idProd + " no existe.");
            }
            if (p.getStock() < cantidadRequerida) {
                throw new SinStockException("No hay stock suficiente de: " + p.getNombre() +
                        ". Stock actual: " + p.getStock() +
                        ", Solicitado: " + cantidadRequerida);
            }
        }

        // 3. RESTAR STOCK y armar la lista final de entidades a guardar
        List<Producto> productosParaFacturar = new ArrayList<>();
        for (Long idProd : ventaReq.getProductosVendidos()) {
            Producto p = repository.consultarProductoPorId(idProd);
            p.setStock(p.getStock() - 1); // Descontamos 1 por cada ID en la lista
            repository.modificarProducto(p); // Actualizamos en el repositorio
            productosParaFacturar.add(p);
        }

        // 4. Calcular Total con Reglas de Negocio
        double totalCalculado = calcularTotal(productosParaFacturar);

        // 5. Crear la entidad Venta y guardarla
        Venta nuevaVenta = new Venta();
        nuevaVenta.setFecha(LocalDate.now());
        nuevaVenta.setProductosVendidos(productosParaFacturar);
        nuevaVenta.setTotal(totalCalculado);

        Venta ventaGuardada = repository.registrarVenta(nuevaVenta);

        // 6. Retornar el DTO para el cliente
        return ventaToVentaDto(ventaGuardada);
    }

    public double calcularTotal(List<Producto> productosVendidos) {
        // Suma base de precios
        double total = productosVendidos.stream()
                .mapToDouble(Producto::getPrecio)
                .sum();

        // Contar cuántos muffins y cafés hay
        long cantidadMuffins = productosVendidos.stream()
                .filter(p -> p.getNombre().equalsIgnoreCase("Muffins Locos"))
                .count();
        long cantidadCafes = productosVendidos.stream()
                .filter(p -> p.getNombre().equalsIgnoreCase("Café"))
                .count();

        // Regla: Si hay más de 5 cafés, 2x1 en uno de ellos (descontamos el precio de 1 café)
        if (cantidadCafes > 5) {
            double precioCafe = productosVendidos.stream()
                    .filter(p -> p.getNombre().equalsIgnoreCase("Café"))
                    .findFirst()
                    .map(Producto::getPrecio)
                    .orElse(0.0);
            total -= precioCafe;
        }

        // Regla: Si hay más de 3 muffins, 10% de descuento en el total
        if (cantidadMuffins > 3) {
            total = total * 0.90; // Multiplicar por 0.90 aplica un 10% de descuento
        }

        return total;
    }

    public VentaDto consultarVentaPorId(Long id) {
        Venta venta = repository.consultarVentaPorId(id)
                .orElseThrow(() -> new VentaNoEncontradaException("La venta con el ID " + id + " no se ha encontrado"));
        return ventaToVentaDto(venta);
    }

    // =====================================
    //           MAPPERS (CONVERSORES)
    // =====================================

    // Convierte una entidad Producto a ProductoDto (oculta el stock)
    public ProductoDto productoToDto(Producto producto) {
        ProductoDto dto = new ProductoDto();
        dto.setId(producto.getId());
        dto.setNombre(producto.getNombre());
        dto.setPrecio(producto.getPrecio());
        return dto;
    }

    // Convierte una entidad Venta a VentaDto
    public VentaDto ventaToVentaDto(Venta venta) {
        VentaDto dto = new VentaDto();
        dto.setId(venta.getId());
        dto.setFecha(venta.getFecha());
        dto.setTotal(venta.getTotal());

        // Transformar todos los productos de la venta en ProductoDto
        List<ProductoDto> listaDto = venta.getProductosVendidos().stream()
                .map(this::productoToDto)
                .toList();

        dto.setProductosVendidos(listaDto);
        return dto;
    }
}