package ru.skypro.homework.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.AdsDto;
import ru.skypro.homework.dto.CreateAdsDto;
import ru.skypro.homework.dto.FullAdsDto;
import ru.skypro.homework.dto.ResponseWrapperAdsDto;
import ru.skypro.homework.entity.Ads;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.mapper.AdsMapper;
import ru.skypro.homework.mapper.ResponseWrapperAdsDtoMapper;
import ru.skypro.homework.repository.AdsRepository;
import ru.skypro.homework.repository.UserRepository;

/**
 * Сервис для работы с сущностью {@link Ads}.
 */
@Slf4j
@Service
public class AdsService {

    @Value("${ads.image.dir.path}")
    private String imageDir;

    AdsRepository adsRepository;

    UserRepository userRepository;

    public AdsService(AdsRepository adsRepository, UserRepository userRepository) {
        this.adsRepository = adsRepository;
        this.userRepository = userRepository;
    }

    /**
     * Метод получает список всех объявлений из {@link AdsRepository}, конвертирует полученный список
     * и отдает результат в виде DTO.
     * @return {@link ResponseWrapperAdsDto}
     */
    public ResponseWrapperAdsDto getAllAds() {
        log.info("Was invoked method - getAllAds");
        return ResponseWrapperAdsDtoMapper.INSTANCE.toResponseWrapperAdsDto(adsRepository.findAll());
    }


    /**
     * Метод принимает необходимые данные для создания объявления, в виде DTO и картинки, на их основе создает сущность
     * {@link Ads} и сохраняет ее в БД. После сохранения возвращает актуальные данные о созданном объявлении в виде DTO.
     * @param properties {@link CreateAdsDto}
     * @param adImage {@link MultipartFile}
     * @return {@link AdsDto}
     */
    public AdsDto createAd(CreateAdsDto properties, MultipartFile adImage) {
        log.info("Was invoked method - createAd");
        Ads newAd = AdsMapper.INSTANCE.createAdsDtoToAds(properties);
        newAd.setAuthor(getAuthUser());
        //Здесь будет метод для получения картинки объявления
        Ads createdAd = adsRepository.save(newAd);
        return AdsMapper.INSTANCE.adsToAdsDto(createdAd);
    }

    /**
     * Метод принимает id искомого объявления, ищет сущность {@link Ads} в БД,
     * возвращает результат поиска в виде DTO или {@code null} в зависимости от результата поиска.
     * @param adId идентификатор объявления
     * @return {@link FullAdsDto} / {@code null}
     */
    public FullAdsDto getInfoAboutAd(int adId) {
        log.info("Was invoked method - getInfoAboutAd");
        Ads ad = adsRepository.findById(adId);
        return (ad == null) ? null : AdsMapper.INSTANCE.adToFullAdsDto(ad);
    }

    /**
     * Метод принимает id объявления, по id удаляет сущность {@link Ads} из БД.
     * @param adId идентификатор объявления
     */
    public void deleteAd(int adId) {
        log.info("Was invoked method - deleteAd");
        adsRepository.deleteById(adId);
    }

    /**
     * Метод принимает id объявления и его актуальные данные, ищет сущность {@link Ads} в БД,
     * если объявление не найдено - возвращает {@code null},
     * если найдено - изменяет данные на актуальные и возвращает новые данные объявления в виде DTO.
     * @param adId идентификатор объявления
     * @param newAdData актуальные данные объявления
     * @return {@link AdsDto} / {@code null}
     */
    public AdsDto updateAd(int adId, CreateAdsDto newAdData) {
        log.info("Was invoked method - updateAd");

        Ads oldAdData = adsRepository.findById(adId);
        if (oldAdData == null) {
            return null;
        }

        oldAdData.setDescription(newAdData.getDescription());
        oldAdData.setPrice(newAdData.getPrice());
        oldAdData.setTitle(newAdData.getTitle());

        Ads updatedAd = adsRepository.save(oldAdData);

        return AdsMapper.INSTANCE.adsToAdsDto(updatedAd);
    }

    /**
     * Метод получает список объявлений авторизованного пользователя из {@link AdsRepository},
     * конвертирует полученный список и отдает результат в виде DTO.
     * @return {@link ResponseWrapperAdsDto}
     */
    public ResponseWrapperAdsDto getMyAds() {
        log.info("Was invoked method - getMyAds");
        return ResponseWrapperAdsDtoMapper.INSTANCE.toResponseWrapperAdsDto(adsRepository.findAllByAuthor(getAuthUser()));
    }

    /**
     * Метод принимает id объявления и его новую картинку, ищет сущность {@link Ads} в БД,
     * если объявление не найдено - возвращает {@code null},
     * если найдено - изменяет картинку на новую и возвращает ссылку на картинку объявления.
     * @param adId идентификатор объявления
     * @param adImage новая картинка объявления
     * @return ссылка на картинку объявления
     */
    public String updateAdImage(int adId, MultipartFile adImage) {
        log.info("Was invoked method - updateAdImage");

        Ads oldAdData = adsRepository.findById(adId);
        if (oldAdData == null) {
            return null;
        }

//        oldAdData.setImage();   здесь будет метод изменения картинки объявления

        Ads updatedAd = adsRepository.save(oldAdData);
        return updatedAd.getImage();
    }

    /**
     * Метод возвращает авторизованного пользователя.
     * @return авторизованный пользователь
     */
    private User getAuthUser() {
        return userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
    }
}
