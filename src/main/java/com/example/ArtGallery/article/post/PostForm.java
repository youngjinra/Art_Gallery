package com.example.ArtGallery.article.post;


import com.example.ArtGallery.article.file.FileEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostForm {
    private String subject;
    private String content;
    private FileEntity fileEntity;
}
