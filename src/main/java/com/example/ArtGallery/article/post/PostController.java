package com.example.ArtGallery.article.post;


import com.example.ArtGallery.article.ImageUtils;
import com.example.ArtGallery.article.file.FileEntity;
import com.example.ArtGallery.article.file.FileService;
import com.example.ArtGallery.user.UserEntity;
import com.example.ArtGallery.user.UserService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RequiredArgsConstructor
@Controller
public class PostController {
    private final FileService fileService;
    private final PostService postService;
    private final UserService userService;

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

    }

    @PreAuthorize("isAuthenticated()")
    // id : 게시물 id, loginUserNick : 로그인한 유저의 닉네임, userNick : 게시물을 올린 유저의 닉네임
    @GetMapping("/post/vote/{id}/{loginUserNick}/{userNick}")
    public String postVote(Model model, @PathVariable("id") Integer id, @PathVariable("loginUserNick") String loginUserNick, @PathVariable("userNick") String userNick) {

        PostEntity postEntity = this.postService.getPostById(id);
        UserEntity user = this.userService.getUserNick(userNick);
        UserEntity loginUser = this.userService.getUserNick(loginUserNick);

        this.postService.vote(postEntity, loginUser);

        String encodedNickname = URLEncoder.encode(userNick, StandardCharsets.UTF_8);

        // 다시 게시물 상세페이지로 리다이렉트 할 때 객체들을 사용할 수 있게 model.addAttribute(); 선언
        // 이 때 article_details_form 템플릿에 이미 있는 객체들 이름에 맞춰서 그에 해당하는 데이터들을 넘겨 줌.
        model.addAttribute("post", postEntity);
        model.addAttribute("loginUser", loginUser);
        model.addAttribute("user", user);

        return String.format("redirect:/article/details/%s/%s", encodedNickname, id);
    }


    // 조회수 증가 메서드, 아직은 따로 제약을 걸어두진 않아서 같은사람이 계속 조회해도 조회수는 늘어남
    @PutMapping("/api/increase-views/{postId}")
    public ResponseEntity<String> postViews(@PathVariable int postId){
        PostEntity postEntity = postService.getPostById(postId);
        if(postEntity != null){
            int currentViews = postEntity.getPostView();
            postEntity.setPostView(currentViews + 1);
            postService.viewPost(postEntity);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    /********* 추가 *********/
    // image 반환하기
    @GetMapping(value = "/post/image/{uuid}/{fileName}", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<byte[]> userSearch(@PathVariable("uuid") String uuid, @PathVariable("fileName") String fileName ) throws IOException {
        String imagePath = "D:\\springBoot\\testDB\\" + uuid + "_" + fileName;
        Path imageFilePath = Paths.get(imagePath);
        byte[] imageBytes = Files.readAllBytes(imageFilePath);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(ImageUtils.getMimeTypeFromFileName(imagePath)));

        headers.setContentLength(imageBytes.length);

        return new ResponseEntity<>(imageBytes, headers, HttpStatus.OK);
    }

    @PostMapping("/post/incrementDownloads/{id}")
    public ResponseEntity<Void> incrementPostDownloads(@PathVariable("id") int postId) {
        postService.incrementPostDownloads(postId);
        return ResponseEntity.ok().build();
    }
}

