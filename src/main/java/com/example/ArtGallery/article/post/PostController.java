package com.example.ArtGallery.article.post;


import com.example.ArtGallery.article.ImageUtils;
import com.example.ArtGallery.article.file.FileEntity;
import com.example.ArtGallery.article.file.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class PostController {
    private final FileService fileService;
    private final PostService postService;

//    @GetMapping("/post/list")
//    public String list(Model model){
//        List<PostEntity> postEntityList = this.postService.getList();
//        model.addAttribute("postList", postEntityList);
//        return "/test/post_list";
//    }

//    @GetMapping("/post/detail/{id}")
//    public String detail(Model model, @PathVariable("id") int id){
//        PostEntity post = this.postService.getPost(id);
//        model.addAttribute("post", post);
//        return "/test/post_detail";
//    }

    @GetMapping("/post/detail/{id}")
    public String detail(Model model, @PathVariable("id") int id){
        PostEntity post = this.postService.getPost(id);
        model.addAttribute("post", post);
        return "/article_details_form";
    }


    // upload 템플릿에서 form태그의 action: 으로 인해 해당 주소 postmapping
    @PostMapping("/post/create/{nickname}")
    public String create(@PathVariable String nickname, @RequestParam("uploadfile")MultipartFile uploadFile, @RequestParam String subject, @RequestParam String content,
                         RedirectAttributes redirectAttributes){

        if(uploadFile != null && !uploadFile.isEmpty()) {
            FileEntity fileEntity = fileService.uploadFile(uploadFile);
            PostEntity post = postService.create(subject, content, fileEntity, nickname);

            if (fileEntity != null) {
                redirectAttributes.addFlashAttribute("file", fileEntity);
            }

            int postId = post.getId();

            // 닉네임이 한글로 입력되면 리다이렉트가 안되는 현상을 방지
            String encodedNickname = URLEncoder.encode(nickname, StandardCharsets.UTF_8);
            return "redirect:/article/details/" + encodedNickname + "/" + postId;
        }

        // 파일 업로드가 없는 경우에는 닉네임으로만 리다이렉트합니다.
        String encodedNickname = URLEncoder.encode(nickname, StandardCharsets.UTF_8);
        return "redirect:/article/details/" + encodedNickname;

//        // 닉네임이 한글로 입력되면 리다이렉트가 안되는 현상을 방지
//        String encodedNickname = URLEncoder.encode(nickname, StandardCharsets.UTF_8);
//        return "redirect:/article/details/" + encodedNickname + "/" + postId;
    }
    /********* 추가 *********/
    // image 반환하기
    @GetMapping(value = "/post/image/{uuid}/{fileName}", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<byte[]> userSearch(@PathVariable("uuid") String uuid, @PathVariable("fileName") String fileName ) throws IOException {
        String imagePath = "C:\\IT\\Gallery_project_DB\\" + uuid + "_" + fileName;
        Path imageFilePath = Paths.get(imagePath);
        byte[] imageBytes = Files.readAllBytes(imageFilePath);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(ImageUtils.getMimeTypeFromFileName(imagePath)));

        headers.setContentLength(imageBytes.length);

        return new ResponseEntity<>(imageBytes, headers, HttpStatus.OK);
    }
}

