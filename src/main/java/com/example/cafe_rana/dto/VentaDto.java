package com.example.cafe_rana.dto;

import com.example.cafe_rana.model.Producto;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
public class VentaDto {
    private Long id;
    private LocalDate fecha;
    // Devolvemos la lista de productos comprados pero en formato DTO para ocultar su stock al cliente
    private List<ProductoDto> productosVendidos;
    private double total;
}