package com.example.ArtGallery.article.comment;


import com.example.ArtGallery.article.post.PostEntity;
import com.example.ArtGallery.article.post.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

    @PostMapping("/article/details/{nickname}/{id}")
    public String createComment(@PathVariable("id") int id, @PathVariable("nickname") String nickname,
                                @RequestParam("content") String content, CommentForm commentForm){

        PostEntity post = this.postService.getPost(id);
        this.commentService.create(post, content);

        String encodedNickname = URLEncoder.encode(nickname, StandardCharsets.UTF_8);

        return String.format("redirect:/article/details/%s/%d", encodedNickname, id);
    }

    @PostMapping("/article/details/{nickname}/{postId}/{commentId}")
    public String createReply(Model model, @PathVariable("postId") int postId, @PathVariable("commentId") int commentId, @RequestParam("replyContent") String replyContent,
                              @PathVariable("nickname") String nickname){
        PostEntity post = this.postService.getPost(postId);

        CommentEntity parentComment = commentService.getComment(commentId);
        commentService.createReply(post, parentComment, replyContent);

        String encodedNickname = URLEncoder.encode(nickname, StandardCharsets.UTF_8);
        return String.format("redirect:/article/details/%s/%d", encodedNickname, postId);
    }

}
