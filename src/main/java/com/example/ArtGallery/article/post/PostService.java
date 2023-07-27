package com.example.ArtGallery.article.post;


import com.example.ArtGallery.DataNotFoundException;
import com.example.ArtGallery.article.file.FileEntity;
import com.example.ArtGallery.article.file.FileService;
import com.example.ArtGallery.follow.FollowRepository;
import com.example.ArtGallery.user.UserEntity;
import com.example.ArtGallery.user.UserRepository;
import com.example.ArtGallery.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PostService {
    private final PostRepository postRepository;
    private final FileService fileService;
    private final UserRepository userRepository;
    private final UserService userService;
    private final FollowRepository followRepository;

    public List<PostEntity> getList(){
        return this.postRepository.findAll();
    }

    public PostEntity getPost(int id){
        Optional<PostEntity> post = this.postRepository.findById(id);
        if(post.isPresent()){
            return post.get();
        } else {
            throw new DataNotFoundException("post not found");
        }
    }

    public PostEntity create(String subject, String content, FileEntity fileEntity, String nickname){
        Optional<UserEntity> userEntity = this.userRepository.findByNickname(nickname);

        PostEntity post = new PostEntity();
        post.setSubject(subject);
        post.setContent(content);
        post.setCreateDate(LocalDateTime.now());
        post.setFileEntity(fileEntity);
        post.setUserEntity(userEntity.get());

        System.out.println("fileRepository 저장됨");
        this.postRepository.save(post);

        return post;
    }

    public PostEntity getPostById(int postId){
        return postRepository.findById(postId).orElse(null);
    }

    /*// 현재 로그인된 사용자가 작성한 게시물만 가져오는 메서드를 추가.
    public List<PostEntity> getPostsByNickname(String nickname) {
        return postRepository.findByUserEntity_Nickname(nickname);
    }*/




    // 게시물 좋아요
    public void vote(PostEntity postEntity, UserEntity userEntity) {
        postEntity.getVoter().add(userEntity);
        postEntity.setPostLike(postEntity.getVoter().size());

        this.postRepository.save(postEntity);
    }

    public void viewPost(PostEntity postEntity){
        postRepository.save(postEntity);
    }



    // 현재 로그인된 사용자가 작성한 게시물만 가져오는 메서드를 추가.
    public List<PostEntity> getPostsByNickname(String nickname) {
        return postRepository.findByUserEntity_Nickname(nickname);
    }


    // root 게시물 정렬
    public List<PostEntity> getSortedPosts(int sortingOption, String usernickname) {
        List<PostEntity> sortedPosts;

        switch (sortingOption) {
            case 1:
                // 최신순으로 정렬
                sortedPosts = postRepository.findAllByOrderByCreateDateDesc();
                break;
            case 2:
                // 인기순으로 정렬
                sortedPosts = postRepository.findAllByOrderByPostLikeDesc();
                break;

            case 3:
                // 팔로잉만 정렬
                Optional<UserEntity> optionalFollower = userRepository.findByNickname(usernickname);
                if (optionalFollower.isPresent()) {
                    UserEntity follower = optionalFollower.get();
                    List<UserEntity> followingUsers = follower.getFollowing();
                    sortedPosts = new ArrayList<>();
                    for (UserEntity followingUser : followingUsers) {
                        sortedPosts.addAll(postRepository.findByUserEntity_Nickname(followingUser.getNickname()));
                    }
                } else {
                    sortedPosts = new ArrayList<>();
                }
                break;

            default:
                // 기본적으로 최신순으로 정렬
                sortedPosts = postRepository.findAllByOrderByCreateDateDesc();
                break;
        }

        return sortedPosts;
    }

    // /user/detail_form 게시물 정렬
    public List<PostEntity> getSortedPosts_userdetail(int sortingOption, String nickname) {
        List<PostEntity> sortedPosts;

        switch (sortingOption) {
            case 1:
                // 최신순으로 정렬
                sortedPosts = postRepository.findAllByUserEntity_NicknameOrderByCreateDateDesc(nickname);
                break;
            case 2:
                // 인기순으로 정렬
                sortedPosts = postRepository.findAllByUserEntity_NicknameOrderByPostLikeDesc(nickname);
                break;
/*
            // 사용안함
            case 3:
                // 팔로잉만 정렬
                Optional<UserEntity> optionalFollower = userRepository.findByNickname(nickname);
                if (optionalFollower.isPresent()) {
                    UserEntity follower = optionalFollower.get();
                    List<UserEntity> followingUsers = follower.getFollowing();
                    sortedPosts = new ArrayList<>();
                    for (UserEntity followingUser : followingUsers) {
                        sortedPosts.addAll(postRepository.findByUserEntity_Nickname(followingUser.getNickname()));
                    }
                } else {
                    sortedPosts = new ArrayList<>();
                }
                break;
*/

            default:
                // 기본적으로 최신순으로 정렬
                sortedPosts = postRepository.findAllByUserEntity_NicknameOrderByCreateDateDesc(nickname);
                break;
        }

        return sortedPosts;
    }




    public List<PostEntity> getAllSortedPosts() {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createDate"));
        Sort sort = Sort.by(sorts);

        return this.postRepository.findAll(sort);
    }



}
