package com.example.ArtGallery.article.comment;

import com.example.ArtGallery.article.post.PostEntity;
import com.example.ArtGallery.article.post.PostService;
import com.example.ArtGallery.user.UserEntity;
import com.example.ArtGallery.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

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

        // 닉네임의 userEntity
        UserEntity userEntity = userService.getUser(encodedNickname);

        // 댓글 생성
        PostEntity post = this.postService.getPost(postId);
        CommentEntity comment = this.commentService.create(post, content, userEntity);
        model.addAttribute("userEntity", userEntity);

        return String.format("redirect:/article/details/%s/%d#comment_%d", encodedNickname, postId, comment.getId());
    }

    @PostMapping("/comment/create/{nickname}/{postId}/{commentId}")
    public String createReply(Model model, @PathVariable("postId") int postId, @PathVariable("commentId") int commentId,
                              @PathVariable("nickname") String nickname, @RequestParam String replyContent) {

        // 닉네임 안정화(한글인식)
        String encodedNickname = URLEncoder.encode(nickname, StandardCharsets.UTF_8);

        // 닉네임의 userEntity
        UserEntity userEntity = userService.getUser(encodedNickname);

        // 답글 생성
        PostEntity post = this.postService.getPost(postId);
        CommentEntity parentComment = this.commentService.getComment(commentId);
        CommentEntity reply = this.commentService.createReply(post, parentComment, replyContent, userEntity);
        model.addAttribute("userEntity", userEntity);

        // 생성한 댓글로 리다이렉트
        return String.format("redirect:/article/details/%s/%d#reply_%d", encodedNickname, postId, reply.getId());
    }

    // 댓글 및 답글 삭제 컨트롤러
    @GetMapping("/comment/delete/{nickname}/{postId}/{commentId}")
    public String deleteCommentOrReply(@PathVariable("nickname") String nickname,
                                       @PathVariable("postId") int postId,
                                       @PathVariable("commentId") int commentId) {

        // 닉네임 안정화(한글인식)
        String encodedNickname = URLEncoder.encode(nickname, StandardCharsets.UTF_8);

        // 닉네임의 userEntity
        UserEntity userEntity = userService.getUser(encodedNickname);

        commentService.deleteComment(commentId);

        return String.format("redirect:/article/details/%s/%d", nickname, postId);
    }

}
