package com.example.cafe_rana.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorDto {
    // Esta clase nos servirá para devolver los errores en un formato JSON bonito
    private String mensaje;
    private int status;
}