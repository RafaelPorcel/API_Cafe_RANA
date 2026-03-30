package com.example.cafe_rana.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Producto {
    private Long id;
    private String nombre;
    private double precio;
    private int stock;
}
