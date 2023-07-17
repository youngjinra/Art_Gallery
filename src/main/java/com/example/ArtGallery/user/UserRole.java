package com.example.ArtGallery.user;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

// 유저들에게 권한을 주는 클래스( 스프링시큐리티에서 특정 기능이나 리소스에 대한 접근 권한을 나타낼 수 있음 )
@Getter
@RequiredArgsConstructor
public enum UserRole {
    ADMIN("ROLE_ADMIN", "admin", "Admin"),
    USER("ROLE_USER", "user", "User"),
    GUEST("ROLE_GUEST", "guest", "Guest");

    private final String value;     // 스프링 시큐리티에서 권한 확인에 사용
    private final String key;       // 주로 권한을 표시할 때 사용
    private final String title;     // 주로 권한을 사용자에게 표시할 떄 사용

}
