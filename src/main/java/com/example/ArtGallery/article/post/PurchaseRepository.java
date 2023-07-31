package com.example.ArtGallery.article.post;

import com.example.ArtGallery.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseRepository extends JpaRepository<PurchaseEntity, Integer> {
    //구매 여부 확인
    boolean existsByUserAndPost(UserEntity user, PostEntity post);
}
