package ru.skypro.homework.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

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
    @ToString.Exclude
    private Ads ad;

    public String getImageApi() {
        return "/images/ads/" + ad.getId();
    }
}
