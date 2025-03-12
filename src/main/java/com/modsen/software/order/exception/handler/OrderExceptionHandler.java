package com.modsen.software.order.exception.handler;

import com.modsen.software.order.exception.ItemNotFoundException;
import com.modsen.software.order.exception.OrderNotFoundException;
import org.modelmapper.spi.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class OrderExceptionHandler {
    @ExceptionHandler({OrderNotFoundException.class, ItemNotFoundException.class})
    public ResponseEntity<ErrorMessage> notFoundException(RuntimeException exception) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorMessage(exception.getMessage()));
    }
}
