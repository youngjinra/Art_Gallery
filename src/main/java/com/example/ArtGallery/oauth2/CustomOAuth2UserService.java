package com.example.ArtGallery.oauth2;


import com.example.ArtGallery.user.UserEntity;
import com.example.ArtGallery.user.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.UUID;

@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    private final UserRepository userRepository;

    public CustomOAuth2UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest){
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();

        OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());

        UserEntity user = saveOrUpdate(attributes);

        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(user.getRoleValue())),
                attributes.getAttributes(),
                attributes.getNameAttributeKey()
        );
    }

    private UserEntity saveOrUpdate(OAuthAttributes attributes){

        // findByEmail(email) 에 해당하는 SiteUser가 없을경우 null반환
        UserEntity user = userRepository.findByEmail(attributes.getEmail())
                .map(entity -> entity.update(attributes.getName(), attributes.getProvider()))
                .orElse(attributes.toEntity());

        if(user.getNickname() == null){
            // 사용자가 로그인한 email로 저장된 SiteUser가 없는경우
            // 새로운 사용자로 처리하고, 랜덤한 값을 할당해 nickname 필드에 저장
            user.setNickname(generateUniqueNickname());
        }
        return userRepository.save(user);
    }

    // 랜덤값 생성 메서드
    private String generateUniqueNickname(){
        // UUID를 사용하여 랜덤한 값을 생성 ( 중복확률 로또보다 훨씬낮음 )
        return UUID.randomUUID().toString();
    }
}
