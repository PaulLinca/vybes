package com.vybes.dto.mapper;

import com.vybes.dto.CommentDTO;
import com.vybes.model.Comment;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = LikeMapper.class)
public interface CommentMapper {

    @Mapping(target = "vybeId", source = "vybe.id")
    CommentDTO transform(Comment comment);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "vybe", ignore = true)
    @Mapping(target = "user", ignore = true)
    Comment transform(CommentDTO comment);
}
