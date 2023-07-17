package com.example.ArtGallery;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/")
    public String root(){
        return "index";
    }

    @GetMapping("/user/login")
    public String login(){
        return "login_form";
    }

    @GetMapping("/user/logout")
    public String logout(){
        return "/";
    }

    @GetMapping("/user/signup")
    public String signup(){
        return "signup_form";
    }

    @GetMapping("/user/detail_form")
    public String userdetail(){
        return "user_detail_form";
    }

    @GetMapping("/article/details")
    public String articleDetails(){
        return "article_details_form";
    }

    @GetMapping("/search")
    public String articleSearch(){
        return "search_form";
    }

    @GetMapping("/by_topic")
    public String pagesByTopic(){
        return "pages_by_topic";
    }

    @GetMapping("/upload")
    public String fileupload(){
        return "upload_form";
    }


}
