package com.crashero.user.adapters.repository;

import com.crashero.user.adapters.model.entity.BlockedTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.OffsetDateTime;
import java.util.Optional;

public interface SpringDataBlockedTokenRepository extends JpaRepository<BlockedTokenEntity, Long> {
    Optional<BlockedTokenEntity> findByAuthToken(String authToken);
    void deleteAllByExpirationTimeBefore(OffsetDateTime now);
}
