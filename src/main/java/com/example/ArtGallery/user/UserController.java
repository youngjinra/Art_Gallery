package com.example.ArtGallery.user;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/user/signup")
    public String signup(UserCreateForm userCreateForm){
        return "signup_form";
    }

    @PostMapping("/user/signup")
    public String signup(@Valid UserCreateForm userCreateForm, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "signup_form";
        } else {
            userService.create(userCreateForm.getUsername(), userCreateForm.getNickname(), userCreateForm.getEmail(), userCreateForm.getPassword1());
        }
        return "redirect:/";
    }

    @GetMapping("/user/login")
    public String newLogin(){
        return "login_form";
    }

    @GetMapping("/user/detail_form/{nickname}")
    public String userdetail(Model model, @PathVariable("nickname") String nickname, Authentication authentication){

        String nicknameConfirm = null;
        String userEmail = null;
        if (authentication != null && authentication.isAuthenticated()) {
            // UserService에서 로그인 유저 닉네임 반환하는 메소드 호출
            nicknameConfirm = userService.getAuthNickname(userEmail, nicknameConfirm, authentication);
        }

        if(nicknameConfirm == null) {   // 비로그인 유저들을 위한 조건 추가
            UserEntity userEntity = this.userService.getUserNick(nickname);
            model.addAttribute("userEntity", userEntity);
        } else {
            // 현재 로그인한 유저의 닉네임과 해당 프로필의 유저의 닉네임을 비교해서 isCurrentUser에 true, false를 반환
            model.addAttribute("isCurrentUser", nicknameConfirm.equals(nickname));

            // 상단 헤더바 부분 내정보 이미지 클릭시 로그인한 해당 유저의 정보 페이지를 이동하기 위해 nicknameConfirm을 그대로 템플릿에 보내줌
            model.addAttribute("nicknameConfirm", nicknameConfirm);

            UserEntity userEntity = this.userService.getUserNick(nickname);
            model.addAttribute("userEntity", userEntity);
        }

        return "user_detail_form";
    }

    @GetMapping("/")
    public String usernav(Model model, Authentication authentication) {
        // 인증된 사용자의 nickname 가져오기
        String nicknameConfirm = null;
        String userEmail = null;
        if (authentication != null && authentication.isAuthenticated()) {
            // UserService에서 로그인 유저 닉네임 반환하는 메소드 호출
            nicknameConfirm = userService.getAuthNickname(userEmail, nicknameConfirm, authentication);

        } else {
            return "index";
        }

        // 해당 nickname을 사용하여 유저 정보 가져오기
        UserEntity userEntity = userService.getUserNick(nicknameConfirm);
        model.addAttribute("userEntity", userEntity);
        return "index";     // 로그인한 상태일 때는 로그인한 유저의 데이터 객체가 인덱스로 넘어감
    }

    @GetMapping("/user/nickname_change_form")
    public String nickchange(UserNicknameModifyForm userNicknameModifyForm, Authentication authentication){

        String nicknameConfirm = null;
        String userEmail = null;
        if (authentication != null && authentication.isAuthenticated()) {
            // UserService에서 로그인 유저 닉네임 반환하는 메소드 호출
            nicknameConfirm = userService.getAuthNickname(userEmail, nicknameConfirm, authentication);

        }

        // 해당 nickname을 사용하여 유저 정보 가져오기
        UserEntity userEntity = userService.getUserNick(nicknameConfirm);
        userNicknameModifyForm.setNickname(userEntity.getNickname());
        return "nick_change_form";
    }

    @PostMapping("/user/nickname_change_form")
    public String nickchangePost(@Valid UserNicknameModifyForm userNicknameModifyForm, Authentication authentication){

        String nicknameConfirm = null;
        String userEmail = null;
        if (authentication != null && authentication.isAuthenticated()) {
            // UserService에서 로그인 유저 닉네임 반환하는 메소드 호출
            nicknameConfirm = userService.getAuthNickname(userEmail, nicknameConfirm, authentication);

        }
        UserEntity userEntity = userService.getUserNick(nicknameConfirm);
        userService.nickModify(userEntity, userNicknameModifyForm.getNickname());

        return "redirect:/";
    }
}
