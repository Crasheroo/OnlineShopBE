package com.crashero.user.adapters.model.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.OffsetDateTime;

@Data
@Entity
@Table(name = "blocked_tokens")
public class BlockedTokenEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "token")
    private String authToken;
    private OffsetDateTime expirationTime;
}
