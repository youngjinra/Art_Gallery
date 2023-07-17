package com.example.ArtGallery.mail;


import com.example.ArtGallery.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class MailController {
    private final MailService mailService;
    private final UserService userService;

    @PostMapping("/user/signup/mailConfirm")
    @ResponseBody
    String mailConfirm(@RequestParam("email") String email) throws Exception {

        String code = mailService.sendSimpleMessage(email);
        System.out.println("인증코드 : " + code);
        return code;
    }

    @PostMapping("/user/signup/checkid") // id 중복검사를 위해 Post요청으로 ajax에서 데이터를 받아옴
    @ResponseBody
    public String checkID(@RequestParam("id") String id){
        boolean isIdExist = userService.checkIdExist(id); // UserService로 데이터를 보내 중복 검사로직을 완료한 값을 반환받는다
        return isIdExist ? "exist" : "available"; // ajax에 처리된 데이터를 "exist" 또는 "available" 문자열로 반환
    }

    @PostMapping("/user/signup/checknickname")
    @ResponseBody
    public String checkNick(@RequestParam("id") String id){
        boolean isNickExist = userService.checkNicknameExists(id);
        return isNickExist ? "exist" : "available";
    }

    @PostMapping("/user/signup/checkemail")
    @ResponseBody
    public String checkEmail(@RequestParam("id") String id){
        boolean isEmailExist = userService.checkEmailExists(id.toLowerCase());  // email을 소문자로 변경해서 비교
        return isEmailExist ? "exist" : "available";
    }
}
