package com.charles.knightonline.model.mapper;

import com.charles.knightonline.model.dto.UserBasicDTO;
import com.charles.knightonline.model.dto.UserDTO;
import com.charles.knightonline.model.dto.UserNameGenderDTO;
import com.charles.knightonline.model.entity.UserEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDTO toDto(UserEntity entity);

    UserEntity toEntity(UserDTO dto);

    UserBasicDTO toBasicDto(UserEntity entity);

    UserEntity toEntity(UserBasicDTO dto);

    UserNameGenderDTO toNameGenderDto(UserEntity entity);

    UserEntity toEntity(UserNameGenderDTO dto);
}
