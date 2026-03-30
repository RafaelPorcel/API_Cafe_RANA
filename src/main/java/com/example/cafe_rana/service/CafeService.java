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

@Service
public class CafeService {
    @Autowired
    CafeRepository repository;

    public ProductoDto registrarProducto(Producto nuevoProducto) {
        Producto producto = repository.registrarProducto(nuevoProducto);
        return productoToDto(producto);
    }

    public void eliminarProducto(Long id) {

        repository.eliminarProducto(id);
    }

    public Producto modificarProducto(Producto producto) {
        repository.modificarProducto(producto);
        return producto;
    }

    public List<ProductoDto> consultarProductos() {

        return repository.consultarProductos().stream()
                .map(this::productoToDto)
                .toList();
    }

    public Venta registrarVenta(CrearVentaDto venta) {
        List<Producto> productosVendidos = new ArrayList<>();
        for (Long idProd : venta.getProductosVendidos()) {
            Producto productoConsultado = repository.consultarProductoPorId(idProd):
            if (productoConsultado == null) {
                throw new ProductoNoEncontradoException("El producto con id: " + idProd + " no se ha encontrado");
            }
            if (productoConsultado.getStock() <= 0) {
                throw new SinStockException("No hay stock de: " + productoConsultado.getNombre());
            }
            productoConsultado.setStock(productoConsultado.getStock() - 1);
            productosVendidos.add(productoConsultado);
        }
        Venta nuevaVenta = new Venta();
        nuevaVenta.setFecha(LocalDate.now());
        nuevaVenta.setProductosVendidos(productosVendidos);
        nuevaVenta.setTotal(calcularTotal(productosVendidos));
    }

    public double calcularTotal(List<Producto> productosVendidos) {
        double total = productosVendidos.stream()
                .mapToDouble(Producto::getPrecio)
                .sum();
        long cantidadMuffins = productosVendidos.stream()
                .filter(p -> p.getNombre().equalsIgnoreCase("Muffins Locos"))
                .count();
        long cantidadCafes = productosVendidos.stream()
                .filter(p -> p.getNombre().equalsIgnoreCase("Café"))
                .count();

        if (cantidadMuffins > 3) {
            total *= 0.9;
        }
        if (cantidadCafes > 5) {
            total -= productosVendidos.stream()
                    .filter(p -> p.getNombre().equalsIgnoreCase("Café"))
                    .findFirst()
                    .map(Producto::getPrecio)
                    .orElse(0.0);
        }
        return total;
    }

    public VentaDto consultarVentaPorId(Long id) {
        return repository.consultarVentaPorId(id)
                .orElseThrow(() -> new VentaNoEncontradaException("La venta con el ID " + id + " no se ha encontrado"));
    }

    // Copia de datos de Producto a ProductoDto
    public ProductoDto productoToDto(Producto producto) {
        ProductoDto productoDto = new ProductoDto();
        productoDto.setId(producto.getId());
        productoDto.setNombre(producto.getNombre());
        productoDto.setPrecio(producto.getPrecio());

        return productoDto;
    }

    // Copia datos de Venta a VentaDto
    public VentaDto ventaToCrearVentaDto(Venta venta) {
        VentaDto ventaDto = new VentaDto();
        ventaDto.set

    }
}
