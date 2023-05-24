package ru.skypro.homework.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String text;
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "ads_id")
    private Ads ads;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
