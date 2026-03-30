package com.example.cafe_rana.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class CrearVentaDto {
    private List<Long> productosVendidos;
}

