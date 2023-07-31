package com.example.ArtGallery.article.file;


import com.example.ArtGallery.article.post.PostEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class FileEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(unique = true)
    private String uuid;

    private String fileName;
    private String contentType;
    private String filePath;

    @OneToOne(mappedBy = "fileEntity")
    private PostEntity postEntity;

    public FileEntity(){

    }

    public FileEntity(String uuid, String fileName, String contentType, String filePath){
        this.uuid = uuid;
        this.fileName = fileName;
        this.contentType = contentType;
        this.filePath = filePath;
    }
}
