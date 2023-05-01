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
    private Integer pk;
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
    private Integer author;

    public Ads(Integer pk, Integer price, String description, String title, Integer author) {
        this.pk = pk;
        this.price = price;
        this.description = description;
        this.title = title;
        this.author = author;
    }

    public Ads() {
    }

    public Integer getPk() {
        return pk;
    }

    public void setPk(Integer pk) {
        this.pk = pk;
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

    public Integer getAuthor() {
        return author;
    }

    public void setAuthor(Integer author) {
        this.author = author;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ads ads = (Ads) o;
        return pk.equals(ads.pk) && Objects.equals(price, ads.price) && Objects.equals(description, ads.description) && Objects.equals(title, ads.title) && Objects.equals(author, ads.author);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pk, price, description, title, author);
    }

    @Override
    public String toString() {
        return "Ads{" +
                "pk=" + pk +
                ", price=" + price +
                ", description='" + description + '\'' +
                ", title='" + title + '\'' +
                ", author=" + author +
                '}';
    }
}
