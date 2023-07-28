package com.example.ArtGallery.article.comment;


import com.example.ArtGallery.DataNotFoundException;
import com.example.ArtGallery.article.post.PostEntity;
import com.example.ArtGallery.user.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
        return this.commentRepository.save(reply);
    }

    public void delete(CommentEntity comment) {
        commentRepository.delete(comment);
    }

}
