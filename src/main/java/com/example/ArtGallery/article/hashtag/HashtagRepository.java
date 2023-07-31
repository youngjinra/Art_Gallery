package com.example.ArtGallery.article.hashtag;

import org.springframework.data.jpa.repository.JpaRepository;

public interface HashtagRepository extends JpaRepository<HashtagEntity, Integer> {
    HashtagEntity findByName(String name);
}
