package com.vybes.service.vybe.mapper;

import com.vybes.service.vybe.dto.CommentDTO;
import com.vybes.service.vybe.dto.VybeDTO;
import com.vybes.service.vybe.entity.Comment;
import com.vybes.service.vybe.entity.Like;
import com.vybes.service.vybe.entity.Vybe;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    @Mapping(target = "vybeId", source = "vybe.id")
    @Mapping(target = "userId", source = "user.userId")
    CommentDTO transform(Comment comment);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "vybe", ignore = true)
    @Mapping(target = "user", ignore = true)
    Comment transform(CommentDTO comment);
}
