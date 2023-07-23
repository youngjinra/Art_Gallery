package com.example.ArtGallery.follow;

import com.example.ArtGallery.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FollowRepository extends JpaRepository<FollowEntity, Integer> {
    FollowEntity findByFollowerAndFollowing(UserEntity follower, UserEntity following);

    int countByFollowerId(int followerId);
    int countByFollowingId(int followingId);
}
