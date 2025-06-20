package com.crashero.user.adapters.mapper;

import com.crashero.user.adapters.model.entity.UserEntity;
import com.crashero.user.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toDomain(UserEntity userEntity);
    UserEntity toEntity(User user);
}
