package com.example.cafe_rana.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProductoDto {
    private Long id;
    private String nombre;
    private double precio;
    // Aquí NO incluimos el 'stock', cumpliendo con la consigna.
}