package com.crashero.cart.adapters.handler;

import com.crashero.cart.adapters.out.persistance.ErrorMessage;
import com.crashero.model.exception.CartException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CartServiceExceptionHandler {
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(CartException.class)
    public ErrorMessage handleCartException(CartException e) {
        return ErrorMessage.buildErrorResponse(e.getMessage(), HttpStatus.CONFLICT);
    }
}
