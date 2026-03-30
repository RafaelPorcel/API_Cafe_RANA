package com.example.cafe_rana.repository;


import com.example.cafe_rana.model.Producto;
import com.example.cafe_rana.model.Venta;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository // Le dice a Spring que esta clase es un Bean de acceso a datos
public class CafeRepository {
    // Usamos Map para simular las tablas de una base de datos
    private Map<Long, Producto> productos = new HashMap<>();
    private Map<Long, Venta> ventas = new HashMap<>();

    // Contadores para simular el auto-incremento de las IDs
    private long contadorProductos = 1L;
    private long contadorVentas = 1L;

    // Bloque de inicialización: carga datos por defecto al arrancar la app
    public CafeRepository() {
        registrarProducto(new Producto(null, "Café", 2.50, 100));
        registrarProducto(new Producto(null, "Muffins Locos", 4.50, 30));
    }

    public Producto registrarProducto(Producto producto) {
        producto.setId(contadorProductos++); // Asignamos ID y luego incrementamos
        productos.put(producto.getId(), producto);
        return producto;
    }

    public void eliminarProducto(Long id) {
        productos.remove(id);
    }

    public Producto modificarProducto(Producto producto) {
        productos.put(producto.getId(), producto);
        return producto;
    }

    public List<Producto> consultarProductos() {
        return new ArrayList<>(productos.values()); // Convertimos los valores del Map a Lista
    }

    public Producto consultarProductoPorId(Long id) {
        return productos.get(id); // Devuelve null si el ID no existe
    }

    public Venta registrarVenta(Venta venta) {
        venta.setId(contadorVentas++);
        ventas.put(venta.getId(), venta);
        return venta;
    }

    public Optional<Venta> consultarVentaPorId(Long id) {
        // Optional es ideal aquí porque una venta podría no existir
        return Optional.ofNullable(ventas.get(id));
    }
}