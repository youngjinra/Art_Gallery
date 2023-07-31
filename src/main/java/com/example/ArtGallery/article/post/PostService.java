package com.example.ArtGallery.article.post;


import com.example.ArtGallery.DataNotFoundException;
import com.example.ArtGallery.PostSpecifications;
import com.example.ArtGallery.article.file.FileEntity;
import com.example.ArtGallery.article.file.FileService;

import com.example.ArtGallery.article.hashtag.HashtagEntity;
import com.example.ArtGallery.article.hashtag.HashtagRepository;

import com.example.ArtGallery.follow.FollowRepository;
import com.example.ArtGallery.user.UserEntity;
import com.example.ArtGallery.user.UserRepository;
import com.example.ArtGallery.user.UserService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PostService {
    private final PostRepository postRepository;
    private final FileService fileService;
    private final UserRepository userRepository;
    private final UserService userService;

    private final HashtagRepository hashtagRepository;
    private final FollowRepository followRepository;

    public List<PostEntity> getList() {
        return this.postRepository.findAll();
    }

    public PostEntity getPost(int id) {
        Optional<PostEntity> post = this.postRepository.findById(id);
        if (post.isPresent()) {
            return post.get();
        } else {
            throw new DataNotFoundException("post not found");
        }
    }

    public PostEntity create(String subject, String content, FileEntity fileEntity, String nickname, Integer category, List<String> hashtags){
        Optional<UserEntity> userEntity = this.userRepository.findByNickname(nickname);

        PostEntity post = new PostEntity();
        post.setSubject(subject);
        post.setContent(content);
        post.setCreateDate(LocalDateTime.now());
        post.setFileEntity(fileEntity);
        post.setUserEntity(userEntity.get());

        post.setCategory(category);

        // 해시태그 추가 로직
        Set<HashtagEntity> hashtagEntities = new HashSet<>();
        for (String hashtagName : hashtags) {
            HashtagEntity existingHashtag = hashtagRepository.findByName(hashtagName);
            if (existingHashtag == null) {
                // 데이터베이스에 존재하지 않는 해시태그라면 추가
                HashtagEntity hashtag = new HashtagEntity();
                hashtag.setName(hashtagName);
                hashtagRepository.save(hashtag);
                hashtagEntities.add(hashtag);
            } else {
                // 이미 데이터베이스에 존재하는 해시태그라면 추가하지 않음
                hashtagEntities.add(existingHashtag);
            }
        }
        post.setHashtags(hashtagEntities);

        System.out.println("fileRepository 저장됨");
        this.postRepository.save(post);

        return post;
    }

    public PostEntity getPostById(int postId) {
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

    // 현재 로그인한 사용자가 해당 게시물에 좋아요를 눌렀는지 여부를 판단
    public boolean isLikedByCurrentUser(int postId, String nickname) {
        Optional<PostEntity> postEntity = this.postRepository.findById(postId);
        PostEntity post = postEntity.get();

        Set<UserEntity> voters = post.getVoter();
        List<String> voterNicknames = voters.stream()
                .map(UserEntity::getNickname)
                .collect(Collectors.toList());

        return voterNicknames.contains(nickname);
    }

    public void viewPost(PostEntity postEntity) {
        postRepository.save(postEntity);
    }

    public void incrementPostDownloads(int postId) {
        PostEntity post = postRepository.findById(postId).orElse(null);
        if (post != null) {
            post.setPostDownloads(post.getPostDownloads() + 1);
            postRepository.save(post);
        }
    }


    public List<String> getTagsByPostId(int postId) {
        // postId를 사용하여 해당 게시물의 태그들을 가져옴
        PostEntity post = postRepository.findById(postId).orElse(null);

        if (post == null) {
            // 게시물이 존재하지 않으면 빈 리스트 반환
            return Collections.emptyList();
        }

        // tags에 해당 게시물의 태그들 추가
        List<String> tags = new ArrayList<>();
        for (HashtagEntity hashtag : post.getHashtags()) {
            tags.add(hashtag.getName());
        }
        return tags;
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
                    List<String> followingNicknames = new ArrayList<>();
                    for (UserEntity followingUser : followingUsers) {
                        followingNicknames.add(followingUser.getNickname());
                    }
                    sortedPosts = postRepository.findByUserEntity_NicknameInOrderByCreateDateDesc(followingNicknames);
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


    // 카테고리별 페이지 정렬
    public List<PostEntity> getSortedPosts_category(int sortingOption, String usernickname, Integer category) {
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
                    List<String> followingNicknames = new ArrayList<>();
                    for (UserEntity followingUser : followingUsers) {
                        followingNicknames.add(followingUser.getNickname());
                    }
                    sortedPosts = postRepository.findByUserEntity_NicknameInOrderByCreateDateDesc(followingNicknames);
                } else {
                    sortedPosts = new ArrayList<>();
                }
                break;

            default:
                // 기본적으로 최신순으로 정렬
                sortedPosts = postRepository.findAllByOrderByCreateDateDesc();
                break;
        }

        // 해당 카테고리에 속하는 게시물만 필터링
        if (category != null) {
            sortedPosts = sortedPosts.stream()
                    .filter(post -> post.getCategory().equals(category))
                    .collect(Collectors.toList());
        }

        return sortedPosts;
    }

    public List<PostEntity> getAllSortedPosts() {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createDate"));
        Sort sort = Sort.by(sorts);

        return this.postRepository.findAll(sort);
    }

/*<<<<<<< HEAD*/

    // 검색 (+정렬)
    public List<PostEntity> getPostsByHashtag(String keywords, int sortingOption){

        List<String> keywordList = Arrays.stream(keywords.split(" ")).collect(Collectors.toList());

        // 검색어를 OR 연산하여 합침
        Specification<PostEntity> searchSpec = Specification.where(null);
        for (String keyword : keywordList) {
            Specification<PostEntity> spec = PostSpecifications.searchAllWithHashtag(keyword);
            searchSpec = searchSpec.or(spec);
        }

        /*Specification<PostEntity> hashtagSpec = PostSpecifications.hasHashtag(keywords);
        Specification<PostEntity> searchSpec = PostSpecifications.searchAll(keyword);*/
        List<PostEntity> sortedPosts;

        switch (sortingOption) {
            case 1:
                // 최신순으로 정렬
                sortedPosts = postRepository.findAll(searchSpec, Sort.by(Sort.Direction.DESC, "createDate"));
                break;
            case 2:
                // 인기순으로 정렬
                sortedPosts = postRepository.findAll(searchSpec, Sort.by(Sort.Direction.DESC, "postLike"));
                break;

            default:
                // 기본적으로 최신순으로 정렬
                sortedPosts = postRepository.findAll(searchSpec, Sort.by(Sort.Direction.DESC, "createDate"));
                break;
        }

        return sortedPosts;
    }

    // 삭제
    @Transactional
    public void deletePost(int postId) {
        Optional<PostEntity> postEntity = this.postRepository.findById(postId);
        PostEntity post = postEntity.get();

        post.setHashtags(null);
        this.postRepository.delete(post);
    }

    // 수정
    public void updatePost(int postId, String subject, String content, List<String> hashtags) {
        Optional<PostEntity> postEntity = this.postRepository.findById(postId);
        PostEntity post = postEntity.get();

        post.setSubject(subject);
        post.setContent(content);

        // 기존 해시태그들 일단 모두 관계 해제
        post.setHashtags(null);

        // 다시 해시태그 추가
        Set<HashtagEntity> hashtagEntities = new HashSet<>();
        for (String hashtagName : hashtags) {
            HashtagEntity existingHashtag = hashtagRepository.findByName(hashtagName);
            if (existingHashtag == null) {
                // 데이터베이스에 존재하지 않는 해시태그라면 추가
                HashtagEntity hashtag = new HashtagEntity();
                hashtag.setName(hashtagName);
                hashtagRepository.save(hashtag);
                hashtagEntities.add(hashtag);
            } else {
                // 이미 데이터베이스에 존재하는 해시태그라면 추가하지 않음
                hashtagEntities.add(existingHashtag);
            }
        }
        post.setHashtags(hashtagEntities);

        this.postRepository.save(post); // 수정된 게시물 저장
    }



    public List<PostEntity> getPostsByCollectionIds(List<Integer> collectionIds) {
        if (collectionIds.isEmpty()) {
            return new ArrayList<>(); // 컬렉션 ID 목록이 비어있는 경우 빈 리스트 반환
        }

        return postRepository.findByIdIn(collectionIds);
    }
}
