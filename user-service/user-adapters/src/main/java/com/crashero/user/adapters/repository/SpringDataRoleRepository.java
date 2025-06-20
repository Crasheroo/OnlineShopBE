package com.crashero.user.adapters.repository;

import com.crashero.user.adapters.model.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataRoleRepository extends JpaRepository<RoleEntity, Long> {
}
