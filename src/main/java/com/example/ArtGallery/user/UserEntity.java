package com.example.ArtGallery.user;


import com.example.ArtGallery.article.post.PostEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;     // PrimaryKey ID (고유값)

    @Column(unique = true)
    private String username;  // 로컬 유저들이 사용하는 로그인 Id

    @Column(unique = true)
    private String nickname;    // 실제 서버에서 유저들을 구분할 수 있는 요소

    @Column
    private String socialNick; // 소셜 로그인한 유저들에 대해 해당 로그인API 플랫폼 내의 닉네임, 나중에 필요할 수도 있으니 해당 필드에 저장.

    private String password;

    @Column(unique = true)
    private String email; // email도 유저들을 구분하는 중요 요소이기에 unique = true 설정

    @Enumerated(EnumType.STRING)
    private UserRole role = UserRole.GUEST; // 비회원들이 이용할때 기본적으로 GUEST권한을 default로 놓음

    @OneToMany(mappedBy = "userEntity", cascade = CascadeType.REMOVE)
    private List<PostEntity> postEntity;

    private String provider; // Local(로컬유져) kakao(카카오로그인) google(구글로그인) naver(네이버로그인) 4가지로 구분(중요한 구분 요소는 아님)

    private int userpoint; // 각 유저들의 보유 포인트 > 사용처나 적립기능 추후 구현

    private int following; // 해당 유저의 팔로잉 수 > 추후 구현

    private int follower; // 해당 유저의 팔로워 수 > 추후 구현

    private int articlelikes; // 해당 유저가 올린 게시물들의 총 좋아요 받은 수 > 추후 구현

    private int articleviews; // 해당 유저가 올린 게시물들의 총 조회 수 > 추후 구현

    private int usercollection; // 해당 유저가 즐겨찾기 해놓은 게시물 총 개수 > 추후 구현

    public String getRoleValue(){
        return this.role.getValue();    // UserRole 클래스에서 Value속성을 활용하기 위함
    }

    public UserEntity update(String socialNick, String provider){   // 동일한 email의 유저가 다른 플랫폼의 소셜로그인 시 유저 속성 update를 위한 메소드
        this.socialNick = socialNick;
        this.provider = provider;
        return this;
    }
    public UserEntity(){

    }
    @Builder
    public UserEntity(int id, String socialNick, String email, String nickname, UserRole role, String provider){
        this.id = id;
        this.socialNick = socialNick;
        this.email = email;
        this.nickname = nickname;
        this.role = role;
        this.provider = provider;
    }
}
