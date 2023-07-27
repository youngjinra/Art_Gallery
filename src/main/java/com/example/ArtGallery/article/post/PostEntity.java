package com.example.ArtGallery.article.post;


import com.example.ArtGallery.article.comment.CommentEntity;
import com.example.ArtGallery.article.file.FileEntity;
import com.example.ArtGallery.article.hashtag.HashtagEntity;
import com.example.ArtGallery.user.UserEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    private List<CommentEntity> commentList;

    @ManyToOne
    private UserEntity userEntity;

    @ManyToMany
    Set<UserEntity> voter;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "post_hashtag",
            joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "hashtag_id")
    )
    private Set<HashtagEntity> hashtags = new HashSet<>();

    private int postView;
    private int postLike;
    private int postDownloads;
    private int postCollects;

    private Integer category;
}
