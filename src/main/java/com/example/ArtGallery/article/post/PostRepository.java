package com.example.ArtGallery.article.post;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<PostEntity, Integer> {
    List<PostEntity> findByUserEntity_Nickname(String nickname);
}
