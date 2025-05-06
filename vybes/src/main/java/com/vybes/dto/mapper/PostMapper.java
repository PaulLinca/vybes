package com.vybes.dto.mapper;

import com.vybes.dto.AlbumReviewDTO;
import com.vybes.dto.PostDTO;
import com.vybes.dto.VybeDTO;
import com.vybes.model.AlbumReview;
import com.vybes.model.Post;
import com.vybes.model.Vybe;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
        componentModel = "spring",
        uses = {LikeMapper.class, CommentMapper.class, ArtistMapper.class})
public interface PostMapper {

    default PostDTO toPostDTO(Post post) {
        if (post instanceof Vybe vybe) {
            return transform(vybe);
        } else if (post instanceof AlbumReview albumReview) {
            return transform(albumReview);
        }
        return null;
    }

    VybeDTO transform(Vybe vybe);

    @Mapping(source = "spotifyArtists", target = "artists")
    AlbumReviewDTO transform(AlbumReview albumReview);
}
