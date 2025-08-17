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
        uses = {LikeMapper.class, CommentMapper.class, ArtistMapper.class, TrackReviewMapper.class, UserMapper.class})
public interface PostMapper {

    default PostDTO transformToDTO(Post post) {
        if (post instanceof Vybe vybe) {
            return transformToDTO(vybe);
        } else if (post instanceof AlbumReview albumReview) {
            return transformToDTO(albumReview);
        }
        return null;
    }

    @Mapping(target = "songName", source = "track.name")
    @Mapping(target = "spotifyId", source = "track.externalId")
    @Mapping(target = "spotifyArtists", source = "track.artists")
    @Mapping(target = "spotifyAlbumId", source = "album.externalId")
    @Mapping(target = "imageUrl", source = "album.imageUrl")
    VybeDTO transformToDTO(Vybe vybe);

    @Mapping(target = "albumName", source = "album.name")
    @Mapping(target = "spotifyId", source = "album.externalId")
    @Mapping(target = "imageUrl", source = "album.imageUrl")
    @Mapping(target = "releaseDate", source = "album.releaseDate")
    @Mapping(target = "artists", source = "album.artists")
    AlbumReviewDTO transformToDTO(AlbumReview albumReview);
}
