package com.crashero.user.model;

import lombok.Builder;
import java.time.LocalDateTime;

@Builder
public record ErrorMessage(
        String message,
        LocalDateTime errorTime
) {
    public static ErrorMessage buildErrorResponse(String message) {
        return ErrorMessage.builder()
                .message(message)
                .errorTime(LocalDateTime.now())
                .build();
    }
}
