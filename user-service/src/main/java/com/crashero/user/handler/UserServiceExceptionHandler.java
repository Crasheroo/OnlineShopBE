package com.crashero.user.handler;

import com.crashero.model.exception.CartException;
import com.crashero.model.exception.OrderException;
import com.crashero.model.exception.ProductException;
import com.crashero.user.model.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class UserServiceExceptionHandler {
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(ProductException.class)
    public ErrorMessage handleProductException(ProductException e) {
        return ErrorMessage.buildErrorResponse(e.getMessage(), HttpStatus.CONFLICT);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(CartException.class)
    public ErrorMessage handleCartException(ProductException e) {
        return ErrorMessage.buildErrorResponse(e.getMessage(), HttpStatus.CONFLICT);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(OrderException.class)
    public ErrorMessage handleOrderException(ProductException e) {
        return ErrorMessage.buildErrorResponse(e.getMessage(), HttpStatus.CONFLICT);
    }
}
