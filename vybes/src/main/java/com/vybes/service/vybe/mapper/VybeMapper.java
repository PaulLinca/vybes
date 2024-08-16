package com.vybes.service.vybe.mapper;

import com.vybes.dto.CreateVybeRequestDTO;
import com.vybes.service.vybe.dto.VybeDTO;
import com.vybes.service.vybe.entity.Comment;
import com.vybes.service.vybe.entity.Like;
import com.vybes.service.vybe.entity.Vybe;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface VybeMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "comments", ignore = true)
    @Mapping(target = "likes", ignore = true)
    @Mapping(target = "user", ignore = true)
    Vybe transform(CreateVybeRequestDTO vybe);

    @Mapping(target = "userId", source = "user.userId")
    @Mapping(target = "commentIds", source = "comments", qualifiedByName = "mapCommentIds")
    @Mapping(target = "likeIds", source = "likes", qualifiedByName = "mapLikeIds")
    VybeDTO transform(Vybe vybe);

    @Named("mapCommentIds")
    default List<Long> mapCommentIds(List<Comment> comments) {
        return comments.stream().map(Comment::getId).toList();
    }

    @Named("mapLikeIds")
    default List<Long> mapLikeIds(List<Like> likes) {
        return likes.stream().map(Like::getId).toList();
    }
}
