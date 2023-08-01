package com.example.ArtGallery.article.comment;

import com.example.ArtGallery.article.post.PostEntity;
import com.example.ArtGallery.article.post.PostService;
import com.example.ArtGallery.user.UserEntity;
import com.example.ArtGallery.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;



@RequiredArgsConstructor
@Controller
public class CommentController {

    private final PostService postService;
    private final CommentService commentService;
    private final UserService userService;

    @PostMapping("/comment/create/{nickname}/{postId}")
    public String createComment(Model model, @PathVariable("postId") int postId, @PathVariable("nickname") String nickname,
                                @RequestParam String content) {

        // 닉네임의 userEntity
        UserEntity userEntity = userService.getUser(nickname);

        // 댓글 생성
        PostEntity post = this.postService.getPost(postId);
        CommentEntity comment = this.commentService.create(post, content, userEntity);

        return String.format("redirect:/article/details/%s/%d#comment_%d", post.getUserEntity().getNickname(), postId, comment.getId());
    }

    @PostMapping("/comment/create/{nickname}/{postId}/{commentId}")
    public String createReply(Model model, @PathVariable("postId") int postId, @PathVariable("commentId") int commentId,
                              @PathVariable("nickname") String nickname, @RequestParam String replyContent) {

        // 닉네임의 userEntity
        UserEntity userEntity = userService.getUser(nickname);

        // 답글 생성
        PostEntity post = this.postService.getPost(postId);
        CommentEntity parentComment = this.commentService.getComment(commentId);
        CommentEntity reply = this.commentService.createReply(post, parentComment, replyContent, userEntity);

        // 생성한 댓글로 리다이렉트
        return String.format("redirect:/article/details/%s/%d#reply_%d", post.getUserEntity().getNickname(), postId, reply.getId());
    }

    // 댓글 및 답글 삭제 컨트롤러
    @ResponseBody
    @GetMapping("/comment/delete/{nickname}/{postId}/{commentId}")
    public ResponseEntity<String> deleteCommentOrReply(@PathVariable("nickname") String nickname,
                                                       @PathVariable("postId") int postId,
                                                       @PathVariable("commentId") int commentId) {

        commentService.deleteCommentOrReply(commentId);

        return ResponseEntity.ok().body("{\"message\": \"댓글이 성공적으로 삭제되었습니다.\"}");
    }

    // 수정 기능
    @GetMapping("/comment/modify/{postId}/{commentId}")
    public String commentModify(Model model, CommentForm commentForm, @PathVariable("commentId") Integer commentId,
                                @PathVariable("postId") int postId) {

        CommentEntity commentEntity = this.commentService.getComment(commentId);
        commentForm.setContent(commentEntity.getContent());

        model.addAttribute("postId", postId);
        model.addAttribute("commentId", commentId);
        return "comment_form";
    }

    @PostMapping("/comment/modify/{postId}/{commentId}")
    public String commentModify(@Valid CommentForm commentForm, BindingResult bindingResult,
                                @PathVariable("commentId") Integer commentId, @PathVariable("postId") int postId) {
        if (bindingResult.hasErrors()) {
            return "comment_form"; // 유효성 검사 오류가 있을 경우, 같은 폼 뷰를 다시 보여주어 에러 메시지를 표시합니다.
        }

        CommentEntity commentEntity = this.commentService.getComment(commentId);

        PostEntity post = this.postService.getPostById(postId);

        this.commentService.modify(commentEntity, commentForm.getContent());

        return "redirect:/article/details/" + post.getUserEntity().getNickname() + "/" + post.getId() + "#comment_" + commentId;
    }

}
