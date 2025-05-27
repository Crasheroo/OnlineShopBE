package com.crashero.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Invoice {
    private Long id;
    private Long userId;
    private Long orderId;
    private Double amount;
    private LocalDateTime issuedAt;
}
