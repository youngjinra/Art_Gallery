package com.example.ArtGallery.article.comment;


import com.example.ArtGallery.article.post.PostEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
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

    // 댓글(부모) - 답글(자식) 설정
    @OneToMany(mappedBy = "parent", orphanRemoval = true)
    @OrderBy("id DESC")
    private List<CommentEntity> commentList = new ArrayList<>();

    private LocalDateTime createDate;

    @ManyToOne
    private PostEntity postEntity;

    private int depth;

    public int getDepth(){
        return depth;
    }
    public void setDepth(int depth){
        this.depth = depth;
    }
}
