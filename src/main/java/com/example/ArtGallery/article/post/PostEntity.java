package com.example.ArtGallery.article.post;

import com.example.ArtGallery.article.comment.CommentEntity;
import com.example.ArtGallery.article.file.FileEntity;
import com.example.ArtGallery.user.UserEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
public class PostEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(length = 200)
    private String subject;

    @Column(columnDefinition = "TEXT")
    private String content;

    private LocalDateTime createDate;

    @OneToOne
    private FileEntity fileEntity;

    @OneToMany(mappedBy = "postEntity", cascade = CascadeType.REMOVE)
    @Where(clause = "parent_id is null")
    @OrderBy("id DESC")
    private List<CommentEntity> commentList;

    @ManyToOne
    private UserEntity userEntity;

}
