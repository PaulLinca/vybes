package com.vybes.service.challenge;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ChallengeOptionResolver {
//    private final ChallengeOptionRepository challengeOptionRepository;
//    private final AlbumRepository albumRepository;
//    private final TrackRepository trackRepository;
//    private final ArtistRepository artistRepository;
//    private final SpotifyService spotifyService;
//
//    private List<ChallengeOption> processOptions(
//            Challenge challenge,
//            List<ChallengeOptionRequestDTO> optionRequests,
//            AnswerType answerType) throws SpotifyDataException {
//        List<ChallengeOption> options = new ArrayList<>();
//
//        for (ChallengeOptionRequestDTO optionRequest : optionRequests) {
//            ChallengeOption option = new ChallengeOption();
//            option.setChallenge(challenge);
//
//            switch (answerType) {
//                case ALBUM:
//                    if (optionRequest.getSpotifyAlbumId() != null) {
//                        Long albumId = resolveAlbum(optionRequest.getSpotifyAlbumId());
//                        option.setAlbumId(albumId);
//                    } else {
//                        throw new BadRequestException("Album ID required for ALBUM challenge type");
//                    }
//                    break;
//
//                case TRACK:
//                    if (optionRequest.getSpotifyTrackId() != null) {
//                        Long trackId = resolveTrack(optionRequest.getSpotifyTrackId());
//                        option.setTrackId(trackId);
//                    } else {
//                        throw new BadRequestException("Track ID required for TRACK challenge type");
//                    }
//                    break;
//
//                case ARTIST:
//                    if (optionRequest.getSpotifyArtistId() != null) {
//                        Long artistId = resolveArtist(optionRequest.getSpotifyArtistId());
//                        option.setArtistId(artistId);
//                    } else {
//                        throw new BadRequestException(
//                                "Artist ID required for ARTIST challenge type");
//                    }
//                    break;
//
//                case CUSTOM_TEXT:
//                    if (optionRequest.getCustomText() != null
//                            && !optionRequest.getCustomText().trim().isEmpty()) {
//                        option.setCustomText(optionRequest.getCustomText().trim());
//                    } else {
//                        throw new BadRequestException(
//                                "Custom text required for CUSTOM_TEXT challenge type");
//                    }
//                    break;
//
//                default:
//                    throw new BadRequestException("Unsupported answer type: " + answerType);
//            }
//
//            options.add(challengeOptionRepository.save(option));
//        }
//
//        return options;
//    }
//
//    private Long resolveAlbum(String spotifyAlbumId) throws SpotifyDataException {
//        Album existingAlbum = albumRepository.findBySpotifyId(spotifyAlbumId);
//        if (existingAlbum != null) {
//            return existingAlbum.getId();
//        }
//
//        try {
//            Album album = spotifyService.fetchAndSaveAlbum(spotifyAlbumId);
//            return album.getId();
//        } catch (Exception e) {
//            throw new SpotifyDataException(
//                    "Failed to fetch album from Spotify: " + spotifyAlbumId, e);
//        }
//    }
//
//    private Long resolveTrack(String spotifyTrackId) throws SpotifyDataException {
//        Track existingTrack = trackRepository.findBySpotifyId(spotifyTrackId);
//        if (existingTrack != null) {
//            return existingTrack.getId();
//        }
//
//        try {
//            Track track = spotifyService.fetchAndSaveTrack(spotifyTrackId);
//            return track.getId();
//        } catch (Exception e) {
//            throw new SpotifyDataException(
//                    "Failed to fetch track from Spotify: " + spotifyTrackId, e);
//        }
//    }
//
//    private Long resolveArtist(String spotifyArtistId) throws SpotifyDataException {
//        Artist existingArtist = artistRepository.findBySpotifyId(spotifyArtistId);
//        if (existingArtist != null) {
//            return existingArtist.getId();
//        }
//
//        try {
//            Artist artist = spotifyService.fetchAndSaveArtist(spotifyArtistId);
//            return artist.getId();
//        } catch (Exception e) {
//            throw new SpotifyDataException(
//                    "Failed to fetch artist from Spotify: " + spotifyArtistId, e);
//        }
//    }
}
