package com.example.ArtGallery;

import com.example.ArtGallery.article.comment.CommentEntity;
import com.example.ArtGallery.article.comment.CommentRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
class ArtGalleryApplicationTests {

	@Autowired
	private CommentRepository commentRepository;
	@Test
	@Transactional
	void contextLoads() {
//
//		List<CommentEntity> list = commentRepository.findAllDesc();
//
//		for (CommentEntity ce : list) {
//			System.out.println(ce.getContent());
//			for (CommentEntity ce2 : ce.getCommentList()) {
//				System.out.println(ce2.getContent());
//			}
//		}
	}

}
