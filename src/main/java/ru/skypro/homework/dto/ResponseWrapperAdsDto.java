package ru.skypro.homework.dto;

import lombok.Data;

@Data
public class ResponseWrapperAdsDto {
    private Integer count;
    private AdsDto[] result;
}
