package com.example.cafe_rana.repository;


import com.example.cafe_rana.model.Producto;
import com.example.cafe_rana.model.Venta;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class CafeRepository {
    private Map<Long, Producto> productos = new HashMap<>();
    private Map<Long, Venta> ventas = new HashMap<>();
    private long contadorProductos = 3L;
    private long contadorVentas = 1L;

    {
        productos.put(1L, new Producto(1L, "Café", 2.50, 100));
        productos.put(2L, new Producto(2L, "Muffins Locos", 4.50, 30));
    }

    public Producto registrarProducto(Producto producto) {
        producto.setId(contadorProductos++);
        productos.put(producto.getId(), producto);
        return producto;
    }

    public void eliminarProducto(Long id) {
        productos.remove(id);
    }

    public Producto modificarProducto(Producto producto){
        productos.put(producto.getId(), producto);
        return producto;
    }

    public List<Producto> consultarProductos(){
        return new ArrayList<>(productos.values());
    }

    public Producto consultarProductoPorId(Long id) {
        return productos.get(id);
    }

    public Venta registrarVenta(Venta venta){
        venta.setId(contadorVentas++);
        ventas.put(venta.getId(), venta);
        return venta;
    }

    public Optional<Venta> consultarVentaPorId(Long id){
        return Optional.ofNullable(ventas.get(id));
    }
}
