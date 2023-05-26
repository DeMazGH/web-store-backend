package ru.skypro.homework.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private User author;

    @OneToOne(cascade = ALL)
    @JoinColumn(name = "image_id")
    private Image image;

    @OneToMany(mappedBy = "ads", cascade = ALL)
    private List<Comment> comments;

    @Override
    public String toString() {
        return "Ads{" +
                "id=" + id +
                ", price=" + price +
                ", description='" + description + '\'' +
                ", title='" + title + '\'' +
                ", author=" + author.getId() +
                ", image=" + image.getId() +
                ", comments=" + comments +
                '}';
    }
}
