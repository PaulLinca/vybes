package com.vybes.service.vybe.mapper;

import com.vybes.service.vybe.dto.LikeDTO;
import com.vybes.service.vybe.entity.Like;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface LikeMapper {
    @Mapping(source = "vybe.id", target = "vybeId")
    @Mapping(source = "user.userId", target = "userId")
    @Mapping(source = "comment.id", target = "commentId")
    LikeDTO transform(Like like);
}
