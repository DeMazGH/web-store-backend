package ru.skypro.homework.dto;

import lombok.Data;

@Data
public class CommentDTO {
    private Integer author;
    private long createdAt;
    private Integer pk;
    private String text;
}
