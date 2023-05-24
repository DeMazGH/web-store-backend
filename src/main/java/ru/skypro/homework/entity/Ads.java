package ru.skypro.homework.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@Table(name = "ads")
public class Ads {
    /**
     * id объявления
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    /**
     * цена объявления
     */
    private Integer price;
    /**
     * описание объявления
     */
    private String description;
    /**
     * заголовок объявления
     */
    private String title;
    /**
     * id автора объявления
     */
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User author;

    @OneToOne
    @JoinColumn(name = "image_id")
    private Image image;
}
