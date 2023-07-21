package com.example.ArtGallery.question;

import com.example.ArtGallery.DataNotFoundException;
import com.example.ArtGallery.user.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class QuestionService {

    private final QuestionRepository questionRepository;

    public List<Question> getList() {
        return this.questionRepository.findAll();
    }

    public Question getQuestion(Integer id) {
        Optional<Question> question = this.questionRepository.findById(id);
        if (question.isPresent()) {
            return question.get();
        } else {
            throw new DataNotFoundException("question not found");
        }
    }

    public void create(String content, UserEntity user) {
        Question q = new Question();
        q.setContent(content);
        q.setCreateDate(LocalDateTime.now());
        q.setAuthor(user);
        this.questionRepository.save(q);
    }

    public void modify(Question question, String content) {
        question.setContent(content);
        question.setModifyDate(LocalDateTime.now());
        this.questionRepository.save(question);
    }

    public void delete(Question question) {
        this.questionRepository.delete(question);
    }

    public void vote(Question question, UserEntity userEntity) {
        question.getVoter().add(userEntity);
        this.questionRepository.save(question);
    }
}