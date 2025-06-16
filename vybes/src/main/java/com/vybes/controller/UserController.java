package com.vybes.controller;

import com.vybes.dto.PostDTO;
import com.vybes.dto.mapper.PostMapper;
import com.vybes.dto.request.FavoritesUpdateRequest;
import com.vybes.dto.request.UsernameSetupRequestDTO;
import com.vybes.dto.response.PostPageResponse;
import com.vybes.dto.response.VybesUserResponseDTO;
import com.vybes.model.Post;
import com.vybes.model.VybesUser;
import com.vybes.repository.UserRepository;
import com.vybes.service.post.PostService;
import com.vybes.service.user.UserFavoritesService;
import com.vybes.service.user.UserService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("api/user")
@CrossOrigin("*") // check what this means
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final UserService userService;
    private final PostService postService;
    private final PostMapper postMapper;
    private final UserRepository userRepository;
    private final UserFavoritesService userFavoritesService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public VybesUserResponseDTO getUser(@RequestParam String username) {
        return userService.getByUsername(username);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/setUsername")
    public VybesUserResponseDTO setUsername(
            @RequestBody UsernameSetupRequestDTO usernameSetupRequestDTO) {
        return userService.setUsername(usernameSetupRequestDTO.getUsername());
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/setProfilePicture")
    public VybesUserResponseDTO setProfilePicture(@RequestParam("image") MultipartFile image) {
        return userService.setProfilePicture(image);
    }

    @Transactional
    @GetMapping("/profilePicture/{userId}")
    public ResponseEntity<byte[]> getProfilePictureById(@PathVariable Long userId) {
        VybesUser user =
                userRepository
                        .findByUserId(userId)
                        .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        byte[] image = user.getProfilePicture();
        if (image == null || image.length == 0) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(image);
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/updateFavorites")
    public void updateFavorites(@RequestBody FavoritesUpdateRequest favoritesUpdateRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        VybesUser user = userRepository.findByEmail(authentication.getName()).orElseThrow();

        userFavoritesService.updateUserFavorites(user, favoritesUpdateRequest);

        userRepository.save(user);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "{userId}/posts", produces = "application/json; charset=UTF-8")
    public ResponseEntity<PostPageResponse> getUserPosts(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "postedDate") String sort,
            @RequestParam(defaultValue = "DESC") String direction) {
        VybesUser user = userRepository.findByUserId(userId).orElseThrow();

        Page<Post> vybesPage = postService.getPostsPaginatedByUser(user, page, size, sort, direction);

        List<PostDTO> vybesDTOs =
                vybesPage.getContent().stream().map(postMapper::toPostDTO).toList();

        PostPageResponse response =
                new PostPageResponse(
                        vybesDTOs,
                        vybesPage.getNumber(),
                        vybesPage.getSize(),
                        vybesPage.getTotalElements(),
                        vybesPage.getTotalPages(),
                        vybesPage.isLast());

        return ResponseEntity.ok(response);
    }
}
