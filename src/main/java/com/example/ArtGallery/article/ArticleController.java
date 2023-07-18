package com.example.ArtGallery.article;


import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class ArticleController {
    @GetMapping("/upload")
    @PreAuthorize("isAuthenticated()")
    public String fileupload(){
        return "upload_form";
    }
}
