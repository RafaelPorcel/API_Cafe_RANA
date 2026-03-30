package com.example.cafe_rana.exceptions;

import com.example.cafe_rana.dto.ErrorDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

public class GlobalExceptionHandler {

    // Si se lanza SinStockException, este método la captura
    @ExceptionHandler(SinStockException.class)
    public ResponseEntity<ErrorDto> handleSinStockException(SinStockException ex) {
        // Devolvemos un JSON claro con el código 400 (Bad Request)
        ErrorDto error = new ErrorDto(ex.getMessage(), HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    // Captura cuando no se encuentra un producto
    @ExceptionHandler(ProductoNoEncontradoException.class)
    public ResponseEntity<ErrorDto> handleProductoNoEncontradoException(ProductoNoEncontradoException ex) {
        ErrorDto error = new ErrorDto(ex.getMessage(), HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND); // Código 404
    }

    // Captura cuando no se encuentra una venta
    @ExceptionHandler(VentaNoEncontradaException.class)
    public ResponseEntity<ErrorDto> handleVentaNoEncontradaException(VentaNoEncontradaException ex) {
        ErrorDto error = new ErrorDto(ex.getMessage(), HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND); // Código 404
    }
}