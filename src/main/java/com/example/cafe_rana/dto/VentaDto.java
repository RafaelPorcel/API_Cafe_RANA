package com.example.cafe_rana.dto;

import com.example.cafe_rana.model.Producto;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@Data
public class VentaDto {
    private Long idProducto;
    private List<Producto> productosVendidos;
    private double total;
}
