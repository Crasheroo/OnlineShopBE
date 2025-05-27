package com.crashero.product.adapters.out.persistance;

import lombok.Builder;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Builder
public record ErrorMessage(
        String message,
        HttpStatus status,
        LocalDateTime errorTime
) {
    public static ErrorMessage buildErrorResponse(String message, HttpStatus status) {
        return ErrorMessage.builder()
                .message(message)
                .status(status)
                .errorTime(LocalDateTime.now())
                .build();
    }
}
