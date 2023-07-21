package com.example.ArtGallery.article.post;


import com.example.ArtGallery.DataNotFoundException;
import com.example.ArtGallery.article.file.FileEntity;
import com.example.ArtGallery.article.file.FileService;
import com.example.ArtGallery.user.UserEntity;
import com.example.ArtGallery.user.UserRepository;
import com.example.ArtGallery.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class PostService {
    private final PostRepository postRepository;
    private final FileService fileService;
    private final UserRepository userRepository;
    private final UserService userService;

    public List<PostEntity> getList(){
        return this.postRepository.findAll();
    }

    public PostEntity getPost(int id){
        Optional<PostEntity> post = this.postRepository.findById(id);
        if(post.isPresent()){
            return post.get();
        } else {
            throw new DataNotFoundException("post not found");
        }
    }

    public PostEntity create(String subject, String content, FileEntity fileEntity, String nickname){
        Optional<UserEntity> userEntity = this.userRepository.findByNickname(nickname);

        PostEntity post = new PostEntity();
        post.setSubject(subject);
        post.setContent(content);
        post.setCreateDate(LocalDateTime.now());
        post.setFileEntity(fileEntity);
        post.setUserEntity(userEntity.get());

        System.out.println("fileRepository 저장됨");
        this.postRepository.save(post);

        return post;
    }

    public PostEntity getPostById(int postId){
        return postRepository.findById(postId).orElse(null);
    }

    // 현재 로그인된 사용자가 작성한 게시물만 가져오는 메서드를 추가.
    public List<PostEntity> getPostsByNickname(String nickname) {
        return postRepository.findByUserEntity_Nickname(nickname);
    }

}
