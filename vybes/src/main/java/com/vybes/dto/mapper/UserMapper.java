package com.vybes.dto.mapper;

import com.vybes.dto.UserDTO;
import com.vybes.model.VybesUser;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDTO transformToDTO(VybesUser user);
}
