package com.vybes.dto.mapper;

import com.vybes.dto.LikeDTO;
import com.vybes.model.PostLike;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface LikeMapper {
    @Mapping(source = "post.id", target = "postId")
    @Mapping(source = "user.userId", target = "userId")
    LikeDTO transform(PostLike like);
}
