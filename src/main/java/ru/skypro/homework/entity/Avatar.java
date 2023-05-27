package ru.skypro.homework.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;

@Slf4j
@Entity
@Data
@NoArgsConstructor
public class Avatar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String filePath;

    @OneToOne
    @JoinColumn(name = "user_id")
    @ToString.Exclude
    private User user;

    public String getAvatarApi() {
        return "/images/avatar/" + user.getId();
    }
}
