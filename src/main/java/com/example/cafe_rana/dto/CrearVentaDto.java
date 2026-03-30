package com.example.cafe_rana.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class CrearVentaDto {
    // El cliente solo nos envía una lista de IDs de los productos que quiere comprar
    // Ejemplo: [1, 1, 2] (quiere dos cafés y un muffin)
    private List<Long> productosVendidos;
}

