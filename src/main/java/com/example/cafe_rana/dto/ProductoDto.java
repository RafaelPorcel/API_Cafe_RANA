package com.example.cafe_rana.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class ProductoDto {
    private Long id;
    private String nombre;
    private double precio;
}
