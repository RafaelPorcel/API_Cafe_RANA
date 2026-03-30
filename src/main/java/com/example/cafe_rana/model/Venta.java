package com.example.cafe_rana.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Venta {
    private Long id;
    private LocalDate fecha;
    private List<Producto> productosVendidos;
    private double total;


}
