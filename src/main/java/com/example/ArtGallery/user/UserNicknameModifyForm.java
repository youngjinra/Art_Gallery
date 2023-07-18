package com.example.ArtGallery.user;


import jakarta.persistence.Column;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserNicknameModifyForm {

    @NotEmpty(message = "닉네임을 입력해주세요.")
    private String nickname;
}
