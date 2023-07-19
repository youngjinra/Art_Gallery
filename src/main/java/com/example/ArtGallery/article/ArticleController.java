package com.example.ArtGallery.article;


import com.example.ArtGallery.user.UserEntity;
import com.example.ArtGallery.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;

@Controller
@RequiredArgsConstructor
public class ArticleController {

    private final UserService userService;

    @GetMapping("/upload")
    @PreAuthorize("isAuthenticated()")
    public String fileupload(Model model, Authentication authentication){

        // 인증된 사용자의 nickname 가져오기
        String nickname = null;
        String userEmail = null;
        if (authentication != null && authentication.isAuthenticated()) {
            if (authentication instanceof OAuth2AuthenticationToken) {
                // OAuth2 인증 사용자
                OAuth2AuthenticationToken oAuth2AuthenticationToken = (OAuth2AuthenticationToken) authentication;
                // nickname 가져오는 로직 추가

                // naver, google은 else 부분 구문들로 email이 추출되었는데, kakao의 email추출 방법이 달라서 if-else로 조건문 하나 더 달아줌 (kakao else naver,google)
                if (oAuth2AuthenticationToken.getAuthorizedClientRegistrationId().equals("kakao")) {
                    Map<String, Object> attributes = oAuth2AuthenticationToken.getPrincipal().getAttributes();
                    Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");

                    // OAuth2 인증을 통한 사용자는 email값으로 DB 데이터와 비교 후, nickname에 해당 email과 동일한 유저의 nickname을 저장
                    userEmail = (String) kakaoAccount.get("email");
                    nickname = userService.getSocialUserNickname(userEmail);
                } else {
                    // OAuth2 인증을 통한 사용자는 email값으로 DB 데이터와 비교 후, nickname에 해당 email과 동일한 유저의 nickname을 저장
                    userEmail = oAuth2AuthenticationToken.getPrincipal().getAttributes().get("email").toString();
                    nickname = userService.getSocialUserNickname(userEmail);
                }
            } else if (authentication instanceof UsernamePasswordAuthenticationToken) {
                // 로컬 인증 사용자
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = (UsernamePasswordAuthenticationToken) authentication;
                // nickname 가져오는 로직 추가
                userEmail = usernamePasswordAuthenticationToken.getName();
                nickname = userService.getLocalUserNickname(userEmail);
            }
        } else {
            return "index";
        }

        // 해당 nickname을 사용하여 유저 정보 가져오기
        UserEntity userEntity = userService.getUserNick(nickname);
        model.addAttribute("userEntity", userEntity);
        return "upload_form";     // 로그인한 상태일 때는 로그인한 유저의 데이터 객체가 인덱스로 넘어감

    }

    @GetMapping("/article/details")
    public String articleDetail(Model model, Authentication authentication){

        String nicknameConfirm = null;
        String userEmail = null;
        if (authentication != null && authentication.isAuthenticated()) {
            if (authentication instanceof OAuth2AuthenticationToken) {
                // OAuth2 인증 사용자
                OAuth2AuthenticationToken oAuth2AuthenticationToken = (OAuth2AuthenticationToken) authentication;
                // nickname 가져오는 로직 추가

                // naver, google은 else 부분 구문들로 email이 추출되었는데, kakao의 email추출 방법이 달라서 if-else로 조건문 하나 더 달아줌 (kakao else naver,google)
                if (oAuth2AuthenticationToken.getAuthorizedClientRegistrationId().equals("kakao")) {
                    Map<String, Object> attributes = oAuth2AuthenticationToken.getPrincipal().getAttributes();
                    Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");

                    // OAuth2 인증을 통한 사용자는 email값으로 DB 데이터와 비교 후, nickname에 해당 email과 동일한 유저의 nickname을 저장
                    userEmail = (String) kakaoAccount.get("email");
                    nicknameConfirm = userService.getSocialUserNickname(userEmail);
                } else {
                    // OAuth2 인증을 통한 사용자는 email값으로 DB 데이터와 비교 후, nickname에 해당 email과 동일한 유저의 nickname을 저장
                    userEmail = oAuth2AuthenticationToken.getPrincipal().getAttributes().get("email").toString();
                    nicknameConfirm = userService.getSocialUserNickname(userEmail);
                }
            } else if (authentication instanceof UsernamePasswordAuthenticationToken) {
                // 로컬 인증 사용자
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = (UsernamePasswordAuthenticationToken) authentication;
                // nickname 가져오는 로직 추가
                userEmail = usernamePasswordAuthenticationToken.getName();
                nicknameConfirm = userService.getLocalUserNickname(userEmail);
            }
        }

        // 상단 헤더바 부분 내정보 이미지 클릭시 로그인한 해당 유저의 정보 페이지를 이동하기 위해 nicknameConfirm을 그대로 템플릿에 보내줌
        model.addAttribute("nicknameConfirm", nicknameConfirm);

        return "article_details_form";
    }
}
