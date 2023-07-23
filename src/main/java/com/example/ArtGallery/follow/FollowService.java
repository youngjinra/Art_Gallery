package com.example.ArtGallery.follow;


import com.example.ArtGallery.user.UserEntity;
import com.example.ArtGallery.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FollowService {
    private final FollowRepository followRepository;
    private final UserRepository userRepository;

    public void followUser(int followerId, int followingId){
        UserEntity follower = userRepository.findById(followerId).orElse(null);
        UserEntity following = userRepository.findById(followingId).orElse(null);

        if(follower == null || following == null){
            throw new IllegalArgumentException("Invalid followerId or followingId");
        }

        FollowEntity followEntity = new FollowEntity();
        followEntity.setFollower(follower);
        followEntity.setFollowing(following);
        this.followRepository.save(followEntity);
    }

    public void unfollowUser(int followerId, int followingId){
        UserEntity follower = userRepository.findById(followerId).orElse(null);
        UserEntity following = userRepository.findById(followingId).orElse(null);

        if(follower == null || following == null){
            throw new IllegalArgumentException("Invalid followerId or followingId");
        }

        FollowEntity followEntity = followRepository.findByFollowerAndFollowing(follower, following);

        if(followEntity != null){
            this.followRepository.delete(followEntity);
        }
    }

    public boolean isFollowing(UserEntity follower, UserEntity following){
        FollowEntity followEntity = followRepository.findByFollowerAndFollowing(follower, following);
        return followEntity != null;
    }

    public int getFollowerCount(int userId){
        return followRepository.countByFollowingId(userId);
    }

    public int getFollowingCount(int userId){
        return followRepository.countByFollowerId(userId);
    }
}
