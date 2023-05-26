package ru.skypro.homework.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String filePath;

    @OneToOne
    @JoinColumn(name = "ad_id")
    private Ads ad;

    public String getImageApi() {
        return "/images/ads/" + ad.getId();
    }

    @Override
    public String toString() {
        return "Image{" +
                "id=" + id +
                ", filePath='" + filePath + '\'' +
                ", ad=" + ad.getId() +
                '}';
    }
}
