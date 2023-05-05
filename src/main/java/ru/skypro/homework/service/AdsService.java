package ru.skypro.homework.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.AdsDto;
import ru.skypro.homework.dto.CreateAdsDto;
import ru.skypro.homework.dto.FullAdsDto;
import ru.skypro.homework.dto.ResponseWrapperAdsDto;
import ru.skypro.homework.entity.Ads;
import ru.skypro.homework.mapper.AdsMapper;
import ru.skypro.homework.mapper.ResponseWrapperAdsDtoMapper;
import ru.skypro.homework.repository.AdsRepository;
import ru.skypro.homework.repository.UserRepository;

@Slf4j
@Service
public class AdsService {

    AdsRepository adsRepository;

    UserRepository userRepository;

    public AdsService(AdsRepository adsRepository, UserRepository userRepository) {
        this.adsRepository = adsRepository;
        this.userRepository = userRepository;
    }

    public ResponseWrapperAdsDto getAllAds() {
        log.info("Was invoked method - getAllAds");
        return ResponseWrapperAdsDtoMapper.INSTANCE.toResponseWrapperAdsDto(adsRepository.findAll());
    }

    public AdsDto createAd(CreateAdsDto properties, MultipartFile image) {
        log.info("Was invoked method - createAd");
        Ads newAd = AdsMapper.INSTANCE.createAdsDtoToAds(properties);
        newAd.setAuthor(userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName()));
        //Здесь будет метод для получения картинки объявления
        Ads createdAd = adsRepository.save(newAd);
        return AdsMapper.INSTANCE.adsToAdsDto(createdAd);
    }

    public FullAdsDto getInfoAboutAd(int adId) {
        log.info("Was invoked method - getInfoAboutAd");
        Ads ad = adsRepository.findById(adId);
        if (ad == null) {
            return null;
        } else {
            return AdsMapper.INSTANCE.adToFullAdsDto(ad);
        }
    }

    public void deleteAd(int adId) {
        log.info("Was invoked method - deleteAd");
        adsRepository.deleteById(adId);
    }

    public AdsDto updateAd(int adId, CreateAdsDto newAdData) {
        log.info("Was invoked method - updateAd");

        Ads oldAdData = adsRepository.findById(adId);
        oldAdData.setDescription(newAdData.getDescription());
        oldAdData.setPrice(newAdData.getPrice());
        oldAdData.setTitle(newAdData.getTitle());

        Ads updatedAd = adsRepository.save(oldAdData);

        return AdsMapper.INSTANCE.adsToAdsDto(updatedAd);
    }
}
