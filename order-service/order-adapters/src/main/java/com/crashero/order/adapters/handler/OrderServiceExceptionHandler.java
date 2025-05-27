package com.crashero.order.adapters.handler;

import com.crashero.model.exception.OrderException;
import com.crashero.order.adapters.out.persistance.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class OrderServiceExceptionHandler {
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(OrderException.class)
    public ErrorMessage handleOrderException(OrderException e) {
        return ErrorMessage.buildErrorResponse(e.getMessage(), HttpStatus.CONFLICT);
    }
}
