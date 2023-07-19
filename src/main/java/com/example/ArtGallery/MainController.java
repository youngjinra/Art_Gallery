package com.example.ArtGallery;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/user/logout")
    public String logout(){
        return "/";
    }

    @GetMapping("/search")
    public String articleSearch(){
        return "search_form";
    }

    @GetMapping("/by_topic")
    public String pagesByTopic(){
        return "pages_by_topic";
    }

}
// askd;ajdlkajlds

//asdnandaosdoanosdandioandandainsdoinsodnasdaisdoiasdnadoansdoad