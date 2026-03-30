package com.example.cafe_rana.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // Lombok genera los Getters, Setters, toString, equals y hashCode automáticamente
@AllArgsConstructor // Genera un constructor con todos los argumentos
@NoArgsConstructor  // Genera un constructor vacío (muy útil para Spring)
public class Producto {
    private Long id;
    private String nombre;
    private double precio;
    private int stock; // El stock real que maneja la base de datos simulada
}