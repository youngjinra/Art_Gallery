package com.example.ArtGallery.article.file;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FileRepository extends JpaRepository<FileEntity, Integer> {
    List<FileEntity> findAll();
}
