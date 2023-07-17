package com.example.ArtGallery.user;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
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
}
