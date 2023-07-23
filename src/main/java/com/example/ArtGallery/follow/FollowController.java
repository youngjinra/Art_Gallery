package com.example.ArtGallery.follow;


import com.example.ArtGallery.article.post.PostEntity;
import com.example.ArtGallery.article.post.PostService;
import com.example.ArtGallery.user.UserEntity;
import com.example.ArtGallery.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class FollowController {
    private final FollowService followService;
    private final UserService userService;
    private final PostService postService;

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/follow/{followerId}/{followingId}")
    public String followUser(@PathVariable int followerId, @PathVariable int followingId, Model model) {

        followService.followUser(followerId, followingId);

        // 리턴값 아무거나 상관없음
        return "index";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/unfollow/{followerId}/{followingId}")
    public String unfollowUser(@PathVariable int followerId, @PathVariable int followingId, Model model) {

        followService.unfollowUser(followerId, followingId);

        return "index";
    }

}
