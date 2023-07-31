package com.example.ArtGallery.article.post;


import com.example.ArtGallery.user.UserEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class PurchaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    private UserEntity user;

    @ManyToOne
    private PostEntity post;

    private LocalDateTime purchaseDate;
}
