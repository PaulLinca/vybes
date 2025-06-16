package com.vybes.dto.mapper;

import com.vybes.dto.CommentDTO;
import com.vybes.dto.CommentLikeDTO;
import com.vybes.model.Comment;
import com.vybes.model.CommentLike;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
        componentModel = "spring",
        uses = {LikeMapper.class})
public interface CommentMapper {

    @Mapping(target = "postId", source = "post.id")
    CommentDTO transform(Comment comment);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "post", ignore = true)
    @Mapping(target = "user", ignore = true)
    Comment transform(CommentDTO comment);

    @Mapping(target = "commentId", source = "comment.id")
    @Mapping(target = "userId", source = "user.userId")
    CommentLikeDTO transform(CommentLike commentLike);
}
