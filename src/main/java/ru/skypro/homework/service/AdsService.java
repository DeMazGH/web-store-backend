package ru.skypro.homework.service;

import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.AdsDto;
import ru.skypro.homework.dto.CreateAdsDto;
import ru.skypro.homework.dto.FullAdsDto;
import ru.skypro.homework.dto.ResponseWrapperAdsDto;

import java.io.IOException;

public interface AdsService {

    ResponseWrapperAdsDto getAllAds();

    AdsDto createAd(CreateAdsDto properties, MultipartFile adImage) throws IOException;

    FullAdsDto getInfoAboutAd(int adId);

    void deleteAd(int adId) throws IOException;

    AdsDto updateAd(int adId, CreateAdsDto newAdData);

    ResponseWrapperAdsDto getMyAds();

    String updateAdImage(int adId, MultipartFile adImage) throws IOException;
}
