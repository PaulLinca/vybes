package com.vybes.dto.mapper;

import com.vybes.dto.UserDTO;
import com.vybes.model.VybesUser;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(
            target = "profilePictureUrl",
            source = "userId",
            qualifiedByName = "buildProfilePictureUrl")
    UserDTO transform(VybesUser user);

    @Named("buildProfilePictureUrl")
    default String buildProfilePictureUrl(Long id) {
        return "https://vybes-service.onrender.com/api/user/profilePicture/" + id;
    }
}
