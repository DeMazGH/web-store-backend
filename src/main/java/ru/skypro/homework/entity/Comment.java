package ru.skypro.homework.entity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
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

    public Comment(Integer id, String text, LocalDateTime createdAt, Ads ads, User user) {
        this.id = id;
        this.text = text;
        this.createdAt = createdAt;
        this.ads = ads;
        this.user = user;
    }

    public Comment() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Ads getAds() {
        return ads;
    }

    public void setAds(Ads ads) {
        this.ads = ads;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Comment comment = (Comment) o;
        return Objects.equals(id, comment.id) && Objects.equals(text, comment.text) && Objects.equals(createdAt, comment.createdAt) && Objects.equals(ads, comment.ads) && Objects.equals(user, comment.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, text, createdAt, ads, user);
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", createdAt=" + createdAt +
                ", ads=" + ads +
                ", user=" + user +
                '}';
    }
}
