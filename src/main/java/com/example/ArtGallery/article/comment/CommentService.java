package com.example.ArtGallery.article.comment;


import com.example.ArtGallery.DataNotFoundException;
import com.example.ArtGallery.article.post.PostEntity;
import com.example.ArtGallery.user.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CommentService {
    private final CommentRepository commentRepository;

    public CommentEntity getComment(int id) {
        Optional<CommentEntity> comment = this.commentRepository.findById(id);
        if (comment.isPresent()) {
            return comment.get();
        } else {
            throw new DataNotFoundException("comment not found");
        }
    }

    public CommentEntity create(PostEntity postEntity, String content, UserEntity userEntity) {
        CommentEntity comment = new CommentEntity();
        comment.setContent(content);
        comment.setCreateDate(LocalDateTime.now());
        comment.setPostEntity(postEntity);
        comment.setUserEntity(userEntity);
        return this.commentRepository.save(comment);
    }

    public CommentEntity createReply(PostEntity postEntity, CommentEntity parentComment, String replyContent, UserEntity userEntity) {
        CommentEntity reply = new CommentEntity();
        reply.setContent(replyContent);
        reply.setCreateDate(LocalDateTime.now());
        reply.setPostEntity(postEntity);
        reply.setParent(parentComment);
        reply.setUserEntity(userEntity);

        // 답글을 생성할 때 부모 댓글의 depth에 1을 더해주는 로직 추가
        reply.setDepth(parentComment.getDepth() + 1);

        return this.commentRepository.save(reply);
    }

    // 댓글 삭제
    public void deleteComment(int commentId) {
        CommentEntity comment = getComment(commentId);
        commentRepository.delete(comment);
    }

}
