package com.example.ArtGallery.article.comment;

import com.example.ArtGallery.article.post.PostEntity;
import com.example.ArtGallery.article.post.PostService;
import com.example.ArtGallery.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.Principal;

@RequiredArgsConstructor
@Controller
public class CommentController {

    private final PostService postService;
    private final CommentService commentService;
    private final UserService userService;

    @PostMapping("/comment/create/{nickname}/{postId}")
    public String createComment(Model model, @PathVariable("postId") int postId, @PathVariable("nickname") String nickname,
                                @RequestParam String content) {

        // 닉네임 안정화(한글인식)
        String encodedNickname = URLEncoder.encode(nickname, StandardCharsets.UTF_8);

        // 댓글 생성
        PostEntity post = this.postService.getPost(postId);
        CommentEntity comment = this.commentService.create(post, content);

        return String.format("redirect:/article/details/%s/%d#comment_%d", encodedNickname, postId, comment.getId());
    }

    @PostMapping("/comment/create/{nickname}/{postId}/{commentId}")
    public String createReply(Model model, @PathVariable("postId") int postId, @PathVariable("commentId") int commentId,
                              @PathVariable("nickname") String nickname, @RequestParam String replyContent) {

        // 닉네임 안정화(한글인식)
        String encodedNickname = URLEncoder.encode(nickname, StandardCharsets.UTF_8);

        // 유효성 검사를 통과한 경우에만 답글 생성
        PostEntity post = this.postService.getPost(postId);
        CommentEntity parentComment = this.commentService.getComment(commentId);
        CommentEntity reply = this.commentService.createReply(post, parentComment, replyContent);

        // 생성한 댓글로 리다이렉트
        return String.format("redirect:/article/details/%s/%d#reply_%d", encodedNickname, postId, reply.getId());
    }

}
