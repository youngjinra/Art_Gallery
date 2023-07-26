package com.example.ArtGallery.article.comment;


import com.example.ArtGallery.DataNotFoundException;
import com.example.ArtGallery.article.post.PostEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CommentService {
    private final CommentRepository commentRepository;

    public CommentEntity getComment(int id){
        Optional<CommentEntity> comment = this.commentRepository.findById(id);
        if(comment.isPresent()){
            return comment.get();
        } else {
            throw new DataNotFoundException("comment not found");
        }
    }

//    public void create(PostEntity postEntity, String content){
//        CommentEntity comment = new CommentEntity();
//        comment.setContent(content);
//        comment.setCreateDate(LocalDateTime.now());
//        comment.setPostEntity(postEntity);
//        this.commentRepository.save(comment);
//    }

    public CommentEntity create(PostEntity postEntity, String content){
        CommentEntity comment = new CommentEntity();
        comment.setContent(content);
        comment.setCreateDate(LocalDateTime.now());
        comment.setPostEntity(postEntity);
        return this.commentRepository.save(comment);
    }

    public void createReply(PostEntity postEntity, CommentEntity parentComment, String replyContent){
        CommentEntity reply = new CommentEntity();
        reply.setContent(replyContent);
        reply.setCreateDate(LocalDateTime.now());
        reply.setPostEntity(postEntity);
        reply.setParent(parentComment);
        this.commentRepository.save(reply);
    }
}
