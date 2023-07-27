package com.example.ArtGallery.article.post;

import com.example.ArtGallery.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface PostRepository extends JpaRepository<PostEntity, Integer> {
    List<PostEntity> findByUserEntity_Nickname(String nickname);

    // 최신순 정렬 DESC
    List<PostEntity> findAllByOrderByCreateDateDesc();
    List<PostEntity> findAllByUserEntity_NicknameOrderByCreateDateDesc(String nickname);

    // 인기(좋아요)순 정렬 DESC
    List<PostEntity> findAllByOrderByPostLikeDesc();
    List<PostEntity> findAllByUserEntity_NicknameOrderByPostLikeDesc(String nickname);

    // 팔로잉 (최신순)정렬 작업중
//    List<PostEntity> findByUserEntityInOrderByCreateDateDesc(List<UserEntity> users);
    List<PostEntity> findByUserEntity_NicknameInOrderByCreateDateDesc(List<String> nicknames);
    List<PostEntity> findByUserEntity(UserEntity userEntity);


    List<PostEntity> findByUserEntity_NicknameInOrderByCreateDateDesc(Collection<String> nicknames);
}
