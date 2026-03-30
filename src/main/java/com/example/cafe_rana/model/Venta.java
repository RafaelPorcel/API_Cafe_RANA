package com.example.cafe_rana.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Venta {
    private Long id;
    private LocalDate fecha;
    // Guardamos la lista de entidades Producto tal cual estaban en el momento de la venta
    private List<Producto> productosVendidos;
    private double total;
}
