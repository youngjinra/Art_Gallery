package com.example.ArtGallery.article.post;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<PostEntity, Integer> {
    List<PostEntity> findByUserEntity_Nickname(String nickname);

    // 최신순 정렬 DESC
    List<PostEntity> findAllByOrderByCreateDateDesc();

    // 인기(좋아요)순 정렬 DESC
    List<PostEntity> findAllByOrderByPostLikeDesc();
}
