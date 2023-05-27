package ru.skypro.homework.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

import static javax.persistence.CascadeType.ALL;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Ads {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer price;
    private String description;
    private String title;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @ToString.Exclude
    private User author;

    @OneToOne(cascade = ALL)
    @JoinColumn(name = "image_id")
    @ToString.Exclude
    private Image image;

    @OneToMany(mappedBy = "ads", cascade = ALL)
    private List<Comment> comments;
}
