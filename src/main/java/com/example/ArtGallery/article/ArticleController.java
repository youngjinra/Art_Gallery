package com.example.ArtGallery.article;

import com.example.ArtGallery.article.comment.CommentForm;
import com.example.ArtGallery.article.comment.CommentReplyForm;
import com.example.ArtGallery.article.comment.CommentService;
import com.example.ArtGallery.article.post.PostEntity;
import com.example.ArtGallery.article.post.PostService;
import com.example.ArtGallery.user.UserEntity;
import com.example.ArtGallery.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequiredArgsConstructor
public class ArticleController {

    private final UserService userService;
    private final PostService postService;
    private final CommentService commentService;

    @GetMapping("/upload")
    @PreAuthorize("isAuthenticated()")
    public String uploadnavbar(Model model, Authentication authentication, RedirectAttributes redirectAttributes) {

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

        // upload 페이지 post요청에 nickname을 사용하기 위해 model추가
        model.addAttribute("nickname", nicknameConfirm);

        return "upload_form";     // 로그인한 상태일 때는 로그인한 유저의 데이터 객체가 인덱스로 넘어감

    }

    @GetMapping("/article/details/{nickname}/{postId}")
    public String articleDetail(Model model, Authentication authentication, @PathVariable String nickname,
                                @PathVariable int postId, CommentForm commentForm, CommentReplyForm commentReplyForm) {

        String nicknameConfirm = null;
        String userEmail = null;
        if (authentication != null && authentication.isAuthenticated()) {
            // UserService에서 로그인 유저 닉네임 반환하는 메소드 호출
            nicknameConfirm = userService.getAuthNickname(userEmail, nicknameConfirm, authentication);
        }

        // 상단 헤더바 부분 내정보 이미지 클릭시 로그인한 해당 유저의 정보 페이지를 이동하기 위해 nicknameConfirm을 그대로 템플릿에 보내줌
        model.addAttribute("nicknameConfirm", nicknameConfirm);

        // 게시물 주인의 닉네임을 템플릿에 반환
        model.addAttribute("nickname", nickname);

        PostEntity post = this.postService.getPost(postId);
        model.addAttribute("post", post);

        return "article_details_form";
    }

    @GetMapping("/by_topic")
    public String bytopic(Model model, Authentication authentication) {

        String nicknameConfirm = null;
        String userEmail = null;
        if (authentication != null && authentication.isAuthenticated()) {
            // UserService에서 로그인 유저 닉네임 반환하는 메소드 호출
            nicknameConfirm = userService.getAuthNickname(userEmail, nicknameConfirm, authentication);

        }

        // 상단 헤더바 부분 내정보 이미지 클릭시 로그인한 해당 유저의 정보 페이지를 이동하기 위해 nicknameConfirm을 그대로 템플릿에 보내줌
        model.addAttribute("nicknameConfirm", nicknameConfirm);

        return "pages_by_topic";
    }

    @GetMapping("/search")
    public String searchform(Model model, Authentication authentication) {

        String nicknameConfirm = null;
        String userEmail = null;
        if (authentication != null && authentication.isAuthenticated()) {
            // UserService에서 로그인 유저 닉네임 반환하는 메소드 호출
            nicknameConfirm = userService.getAuthNickname(userEmail, nicknameConfirm, authentication);

        }

        // 상단 헤더바 부분 내정보 이미지 클릭시 로그인한 해당 유저의 정보 페이지를 이동하기 위해 nicknameConfirm을 그대로 템플릿에 보내줌
        model.addAttribute("nicknameConfirm", nicknameConfirm);

        return "search_form";
    }
}
