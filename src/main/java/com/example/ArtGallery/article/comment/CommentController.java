package com.example.ArtGallery.article.comment;

import com.example.ArtGallery.article.post.PostEntity;
import com.example.ArtGallery.article.post.PostService;
import com.example.ArtGallery.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.hibernate.boot.jaxb.hbm.spi.JaxbHbmRootEntityType;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@RequiredArgsConstructor
@Controller
public class CommentController {

    private final PostService postService;
    private final CommentService commentService;
    private final UserService userService;

    @PostMapping("/comment/create/{nickname}/{postId}")
    public String createComment(Model model, @PathVariable("postId") int postId, @PathVariable("nickname") String nickname,
                                @Valid CommentForm commentForm, BindingResult bindingResult, Authentication authentication,
                                CommentReplyForm commentReplyForm) {

        // 닉네임 안정화(한글인식)
        String encodedNickname = URLEncoder.encode(nickname, StandardCharsets.UTF_8);

        // 유효성 검사
        if (bindingResult.hasErrors()) {

            // 유효성 검사 에러가 있을 경우, 다시 댓글 작성 폼으로 돌아가기
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

            CommentEntity comment = this.commentService.create(post, commentForm.getContent());

            return "/article_details_form";
        }

        // 댓글 생성
        PostEntity post = this.postService.getPost(postId);
        CommentEntity comment = this.commentService.create(post, commentForm.getContent());

        return String.format("redirect:/article/details/%s/%d#comment_%d", encodedNickname, postId, comment.getId());
    }

    @PostMapping("/comment/create/{nickname}/{postId}/{commentId}")
    public String createReply(Model model, @PathVariable("postId") int postId, @PathVariable("commentId") int commentId,
                              @PathVariable("nickname") String nickname, @Valid CommentReplyForm commentReplyForm,
                              BindingResult bindingResult, Authentication authentication, CommentForm commentForm) {

        // 닉네임 안정화(한글인식)
        String encodedNickname = URLEncoder.encode(nickname, StandardCharsets.UTF_8);

        // 유효성 검사
        if (bindingResult.hasErrors()) {

            // 유효성 검사 에러가 있을 경우, 다시 댓글 작성 폼으로 돌아가기
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

            return "/article_details_form";
        }

        // 답글 생성
        PostEntity post = this.postService.getPost(postId);
        CommentEntity parentComment = this.commentService.getComment(commentId);
        CommentEntity reply = this.commentService.createReply(post, parentComment, commentReplyForm.getReplyContent());

        // 생성한 댓글로 리다이렉트
        return String.format("redirect:/article/details/%s/%d#reply_%d", encodedNickname, postId, reply.getId());
    }

}
