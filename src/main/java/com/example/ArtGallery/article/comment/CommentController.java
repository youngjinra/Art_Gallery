package com.example.ArtGallery.article.comment;


import com.example.ArtGallery.article.post.PostEntity;
import com.example.ArtGallery.article.post.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor
@Controller
public class CommentController {

    private final PostService postService;
    private final CommentService commentService;

    @PostMapping("/comment/create/{id}")
    public String createComment(Model model, @PathVariable("id") int id, @RequestParam("content") String content){
        PostEntity post = this.postService.getPost(id);
        this.commentService.create(post, content);
        return String.format("redirect:/test/post/detail/%s", id); // 수정필요
    }

    @PostMapping("/comment/create/{postId}/{commentId}")
    public String createReply(Model model, @PathVariable("postId") int postId, @PathVariable("commentId") int commentId, @RequestParam("replyContent") String replyContent){
        PostEntity post = this.postService.getPost(postId);
        CommentEntity parentComment = commentService.getComment(commentId);
        commentService.createReply(post, parentComment, replyContent);
        return String.format("redirect:/test/post/detail/%s", postId);
    }

}
