package com.example.cafe_rana.controller;

import com.example.cafe_rana.dto.ProductoDto;
import com.example.cafe_rana.dto.VentaDto;
import com.example.cafe_rana.model.Producto;
import com.example.cafe_rana.model.Venta;
import com.example.cafe_rana.service.CafeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CafeController {
    @Autowired
    private CafeService service;

    @PostMapping("/productos")
    public ResponseEntity<ProductoDto> registrarProducto(@RequestBody Producto producto){
        return ResponseEntity.ok(service.registrarProducto(producto));
    }

    @GetMapping("/productos")
    public ResponseEntity<List<ProductoDto>> consultarProductos(){
        return ResponseEntity.ok(service.consultarProductos());
    }

    @PostMapping("/ventas")
    public ResponseEntity<VentaDto> registrarVenta(@RequestBody VentaDto ventaDto){
        return ResponseEntity.ok(service.registrarVenta(ventaDto));
    }

    @GetMapping("/ventas/{id}")
    public ResponseEntity<Venta> consultarVenta (@PathVariable Long id) {
        return ResponseEntity.ok(service.consultarVentaPorId(id));
    }
}