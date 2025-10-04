package com.vybes.service.user;

import com.vybes.dto.mapper.AlbumMapper;
import com.vybes.dto.mapper.ArtistMapper;
import com.vybes.dto.request.ProfilePictureRequestDTO;
import com.vybes.dto.response.VybesUserResponseDTO;
import com.vybes.exception.UserAlreadyExistsException;
import com.vybes.model.VybesUser;
import com.vybes.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final ArtistMapper artistMapper;
    private final AlbumMapper albumMapper;

    public VybesUserResponseDTO getByUsername(String username) throws UsernameNotFoundException {
        var user =
                userRepository
                        .findByUsername(username)
                        .orElseThrow(
                                () ->
                                        new UsernameNotFoundException(
                                                "User not found with username: " + username));

        return VybesUserResponseDTO.builder()
                .userId(user.getUserId())
                .email(user.getEmail())
                .username(user.getUsername())
                .profilePictureUrl(user.getProfilePictureUrl())
                .favoriteArtists(
                        user.getFavoriteArtists().stream()
                                .map(artistMapper::transformToDTO)
                                .collect(Collectors.toSet()))
                .favoriteAlbums(
                        user.getFavoriteAlbums().stream()
                                .map(albumMapper::transformToDTO)
                                .collect(Collectors.toSet()))
                .build();
    }

    public VybesUserResponseDTO setUsername(String username) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        VybesUser user =
                userRepository
                        .findByEmail(authentication.getName())
                        .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        if (user.getUsername() != null) {
            throw new IllegalStateException("Username has already been set");
        }

        if (userRepository.findByUsername(username).isPresent()) {
            throw new UserAlreadyExistsException("Username is already taken");
        }

        user.setUsername(username);
        VybesUser updatedUser = userRepository.save(user);

        return VybesUserResponseDTO.builder()
                .userId(updatedUser.getUserId())
                .email(updatedUser.getEmail())
                .username(updatedUser.getUsername())
                .profilePictureUrl(updatedUser.getProfilePictureUrl())
                .build();
    }

    @SneakyThrows
    public VybesUserResponseDTO setProfilePicture(ProfilePictureRequestDTO requestDTO) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        VybesUser user =
                userRepository
                        .findByEmail(auth.getName())
                        .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        user.setProfilePictureUrl(requestDTO.getProfilePictureUrl());
        VybesUser saved = userRepository.save(user);

        return VybesUserResponseDTO.builder()
                .userId(saved.getUserId())
                .email(saved.getEmail())
                .username(saved.getUsername())
                .profilePictureUrl(saved.getProfilePictureUrl())
                .build();
    }
}
