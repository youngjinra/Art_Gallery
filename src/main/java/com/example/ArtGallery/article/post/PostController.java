package com.example.ArtGallery.article.post;


import com.example.ArtGallery.article.file.FileEntity;
import com.example.ArtGallery.article.file.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class PostController {
    private final FileService fileService;
    private final PostService postService;

    @GetMapping("/post/list")
    public String list(Model model){
        List<PostEntity> postEntityList = this.postService.getList();
        model.addAttribute("postList", postEntityList);
        return "/test/post_list";
    }

    @GetMapping("/post/detail/{id}")
    public String detail(Model model, @PathVariable("id") int id){
        PostEntity post = this.postService.getPost(id);
        model.addAttribute("post", post);
        return "/test/post_detail";
    }


    // upload 템플릿에서 form태그의 action: 으로 인해 해당 주소 postmapping
    @PostMapping("/post/create/{nickname}")
    public String create(@PathVariable String nickname, @RequestParam("uploadfile")MultipartFile uploadFile, @RequestParam String subject, @RequestParam String content,
                         RedirectAttributes redirectAttributes){

        if(uploadFile != null && !uploadFile.isEmpty()) {
            FileEntity fileEntity = fileService.uploadFile(uploadFile);
            PostEntity post = postService.create(subject, content, fileEntity);

            if (fileEntity != null) {
                redirectAttributes.addFlashAttribute("file", fileEntity);
            }
        }

        // 닉네임이 한글로 입력되면 리다이렉트가 안되는 현상을 방지
        String encodedNickname = URLEncoder.encode(nickname, StandardCharsets.UTF_8);
        return "redirect:/user/detail_form/" + encodedNickname;
    }
}
