package com.vybes.service.user;

import com.vybes.dto.response.VybesUserResponseDTO;
import com.vybes.dto.mapper.AlbumMapper;
import com.vybes.dto.mapper.ArtistMapper;
import com.vybes.exception.UserAlreadyExistsException;
import com.vybes.model.VybesUser;
import com.vybes.repository.UserRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final ArtistMapper artistMapper;
    private final AlbumMapper albumMapper;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository
                .findByEmail(email)
                .orElseThrow(
                        () -> new UsernameNotFoundException("User not found with email: " + email));
    }

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
                .favoriteArtists(
                        user.getFavoriteArtists().stream()
                                .map(artistMapper::transform)
                                .collect(Collectors.toSet()))
                .favoriteAlbums(
                        user.getFavoriteAlbums().stream()
                                .map(albumMapper::transform)
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
                .build();
    }
}
