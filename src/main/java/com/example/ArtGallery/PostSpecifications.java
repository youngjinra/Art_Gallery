package com.example.ArtGallery;

import com.example.ArtGallery.article.hashtag.HashtagEntity;
import com.example.ArtGallery.article.post.PostEntity;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;

public class PostSpecifications {

    // Specification : Jpa에서 제공하는 검색기능을 구현하기 위한 인터페이스

    // 제목, 내용, 작성자 이름, 해시태그를 포함한 검색결과
    public static Specification<PostEntity> searchAllWithHashtag(String keyword) {
        return new Specification<>() {
            @Override
            public Predicate toPredicate(Root<PostEntity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                Predicate hashtagPredicate = hasHashtag(keyword).toPredicate(root, query, criteriaBuilder);
                Predicate searchAllPredicate = searchAll(keyword).toPredicate(root, query, criteriaBuilder);

                // 검색 조건을 OR 연산하여 합침
                return criteriaBuilder.or(hashtagPredicate, searchAllPredicate);
            }
        };
    }

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
