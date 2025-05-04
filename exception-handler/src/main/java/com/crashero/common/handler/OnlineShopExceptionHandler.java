package com.crashero.common.handler;

import com.crashero.common.exception.CartException;
import com.crashero.common.exception.OrderException;
import com.crashero.common.exception.ProductException;
import com.crashero.common.model.ErrorMessage;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class OnlineShopExceptionHandler {
    @ExceptionHandler(ProductException.class)
    public ResponseEntity<ErrorMessage> handleProductException(ProductException e) {
        return ResponseEntity
                .status(e.getStatus())
                .body(ErrorMessage.buildErrorResponse(e.getMessage(), e.getStatus()));
    }

    @ExceptionHandler(CartException.class)
    public ResponseEntity<ErrorMessage> handleCartException(CartException e) {
        return ResponseEntity
                .status(e.getStatus())
                .body(ErrorMessage.buildErrorResponse(e.getMessage(), e.getStatus()));
    }

    @ExceptionHandler(OrderException.class)
    public ResponseEntity<ErrorMessage> handleOrderException(OrderException e) {
        return ResponseEntity
                .status(e.getStatus())
                .body(ErrorMessage.buildErrorResponse(e.getMessage(), e.getStatus()));
    }
}
