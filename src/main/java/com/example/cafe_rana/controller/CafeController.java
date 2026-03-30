package com.example.cafe_rana.controller;

import com.example.cafe_rana.dto.CrearVentaDto;
import com.example.cafe_rana.dto.ProductoDto;
import com.example.cafe_rana.dto.VentaDto;
import com.example.cafe_rana.model.Producto;
import com.example.cafe_rana.service.CafeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // Indica que los métodos devuelven datos JSON automáticamente
@RequestMapping("/api") // Todas las rutas empezarán con /api
public class CafeController {

    @Autowired // Inyectamos el servicio para acceder a la lógica de negocio
    private CafeService service;

    // POST /api/productos -> Crear un nuevo producto
    @PostMapping("/productos")
    public ResponseEntity<ProductoDto> registrarProducto(@RequestBody Producto producto){
        // Usamos HttpStatus.CREATED (201) porque es un Alta
        return new ResponseEntity<>(service.registrarProducto(producto), HttpStatus.CREATED);
    }

    // GET /api/productos -> Listar todos los productos
    @GetMapping("/productos")
    public ResponseEntity<List<ProductoDto>> consultarProductos(){
        return ResponseEntity.ok(service.consultarProductos()); // Código 200 OK
    }

    // POST /api/ventas -> Registrar una venta enviando lista de ID de producto
    @PostMapping("/ventas")
    public ResponseEntity<VentaDto> registrarVenta(@RequestBody CrearVentaDto crearVentaDto){
        // Recibe el DTO de creación, procesa la venta y devuelve el DTO de respuesta
        return new ResponseEntity<>(service.registrarVenta(crearVentaDto), HttpStatus.CREATED);
    }

    // GET /api/ventas/{id} -> Consultar una venta específica
    @GetMapping("/ventas/{id}")
    public ResponseEntity<VentaDto> consultarVenta (@PathVariable Long id) {
        // En lugar de devolver la Entidad Venta (que expondría el stock), devolvemos VentaDto
        return ResponseEntity.ok(service.consultarVentaPorId(id));
    }
}