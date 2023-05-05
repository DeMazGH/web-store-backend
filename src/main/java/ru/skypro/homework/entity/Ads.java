package ru.skypro.homework.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
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

    private String image;

    public Ads(Integer id, Integer price, String description, String title, User author, String image) {
        this.id = id;
        this.price = price;
        this.description = description;
        this.title = title;
        this.author = author;
        this.image = image;
    }

    public Ads() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer pk) {
        this.id = pk;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ads ads = (Ads) o;
        return Objects.equals(id, ads.id) && Objects.equals(price, ads.price) && Objects.equals(description, ads.description) && Objects.equals(title, ads.title) && Objects.equals(author, ads.author) && Objects.equals(image, ads.image);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, price, description, title, author, image);
    }

    @Override
    public String toString() {
        return "Ads{" +
                "id=" + id +
                ", price=" + price +
                ", description='" + description + '\'' +
                ", title='" + title + '\'' +
                ", author=" + author +
                ", image='" + image + '\'' +
                '}';
    }
}
