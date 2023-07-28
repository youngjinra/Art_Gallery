package com.example.ArtGallery;

import com.example.ArtGallery.article.hashtag.HashtagEntity;
import com.example.ArtGallery.article.post.PostEntity;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class PostSpecifications {



    // Specification : Jpa에서 제공하는 검색기능을 구현하기 위한 인터페이스
    // 해시태그 검색결과
    public static Specification<PostEntity> hasHashtag(String hashtagName){
        return new Specification<PostEntity>() {
            @Override
            public Predicate toPredicate(Root<PostEntity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                Join<PostEntity, HashtagEntity> hashtagJoin = root.joinSet("hashtags", JoinType.INNER);
                return criteriaBuilder.equal(hashtagJoin.get("name"), hashtagName);
            }
        };
    }

    // 제목, 내용, 작성자 이름을 포함한 검색결과
    public static Specification<PostEntity> searchAll(String keyword) {
        return new Specification<>() {
            @Override
            public Predicate toPredicate(Root<PostEntity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.or(
                        criteriaBuilder.like(root.get("subject"), "%" + keyword + "%"),
                        criteriaBuilder.like(root.get("content"), "%" + keyword + "%"),
                        criteriaBuilder.like(root.get("userEntity").get("nickname"), "%" + keyword + "%")
                );
            }
        };
    }
}
