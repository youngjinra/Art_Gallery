package com.example.ArtGallery.article.comment;

import com.example.ArtGallery.article.post.PostEntity;
import com.example.ArtGallery.user.UserEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
public class CommentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    // 댓글 (부모)
    @ManyToOne
    @JoinColumn(name = "parent_id")
    private CommentEntity parent;

    @Column(columnDefinition = "TEXT")
    private String content;

    // CommentEntity
    @OneToMany(mappedBy = "parent", cascade = CascadeType.REMOVE)
    @OrderBy("id DESC")
    private List<CommentEntity> commentList = new ArrayList<>();

    private LocalDateTime createDate;

    private LocalDateTime modifyDate;

    @ManyToOne
    private PostEntity postEntity;

    @Column(nullable = false)
    private int depth;

    @ManyToOne
    private UserEntity userEntity;

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }
}
