package com.modsen.software.order.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
@AllArgsConstructor
@Getter
public class OrderNotFoundException extends RuntimeException{
    private String message;
}
