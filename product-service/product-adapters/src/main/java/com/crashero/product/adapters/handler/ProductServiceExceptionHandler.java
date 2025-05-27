package com.crashero.product.adapters.handler;

import com.crashero.product.adapters.out.persistance.ErrorMessage;
import com.crashero.model.exception.ProductException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ProductServiceExceptionHandler {
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(ProductException.class)
    public ErrorMessage handleProductException(ProductException e) {
        return ErrorMessage.buildErrorResponse(e.getMessage(), HttpStatus.CONFLICT);
    }
}
