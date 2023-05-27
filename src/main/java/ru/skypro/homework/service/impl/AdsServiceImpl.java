package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.AdsDto;
import ru.skypro.homework.dto.CreateAdsDto;
import ru.skypro.homework.dto.FullAdsDto;
import ru.skypro.homework.dto.ResponseWrapperAdsDto;
import ru.skypro.homework.entity.Ads;
import ru.skypro.homework.entity.Image;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.exception.AdNotFoundException;
import ru.skypro.homework.mapper.AdsMapper;
import ru.skypro.homework.mapper.ResponseWrapperAdsDtoMapper;
import ru.skypro.homework.repository.AdsRepository;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.AdsService;
import ru.skypro.homework.service.ImageService;

import java.io.IOException;

/**
 * Сервис для работы с сущностью {@link Ads}.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AdsServiceImpl implements AdsService {

    private final AdsRepository adsRepository;
    private final UserRepository userRepository;
    private final ImageService imageService;
    private final AdsMapper adsMapper;
    private final ResponseWrapperAdsDtoMapper responseWrapperAdsDtoMapper;

    /**
     * Метод получает список всех объявлений из {@link AdsRepository}, конвертирует полученный список
     * и отдает результат в виде DTO.
     *
     * @return {@link ResponseWrapperAdsDto}
     */
    @Override
    public ResponseWrapperAdsDto getAllAds() {
        log.info("Was invoked method - getAllAds");
        return responseWrapperAdsDtoMapper.toResponseWrapperAdsDto(adsRepository.findAll());
    }

    /**
     * Метод принимает необходимые данные для создания объявления, в виде DTO и картинки, на их основе создает сущность
     * {@link Ads} и сохраняет ее в БД. После сохранения возвращает актуальные данные о созданном объявлении в виде DTO.
     *
     * @param properties {@link CreateAdsDto}
     * @param adImage    {@link MultipartFile}
     * @return {@link AdsDto}
     */
    @Override
    public AdsDto createAd(CreateAdsDto properties, MultipartFile adImage) throws IOException {
        log.info("Was invoked method - createAd");

        Ads newAd = adsMapper.createAdsDtoToAds(properties);
        newAd.setAuthor(getAuthUser());
        Ads createdAd = adsRepository.save(newAd);

        Image image = imageService.saveImage(createdAd, adImage);
        createdAd.setImage(image);
        adsRepository.save(createdAd);

        return adsMapper.adsToAdsDto(createdAd);
    }

    /**
     * Метод принимает id искомого объявления, ищет сущность {@link Ads} в БД,
     * возвращает результат поиска в виде DTO или {@code null} в зависимости от результата поиска.
     *
     * @param adId идентификатор объявления
     * @return {@link FullAdsDto} / {@code null}
     */
    @Override
    public FullAdsDto getInfoAboutAd(int adId) {
        log.info("Was invoked method - getInfoAboutAd");
        Ads ad = adsRepository.findById(adId);
        return (ad == null) ? null : adsMapper.adToFullAdsDto(ad);
    }

    /**
     * Метод принимает id объявления, по id удаляет файл картинки, далее удаляет сущность {@link Ads} из БД.
     * Каскадно удаляются все сущности {@link ru.skypro.homework.entity.Comment} связанные с этим объявлением.
     *
     * @param adId идентификатор объявления
     */
    @Override
    public void deleteAd(int adId) throws IOException {
        log.info("Was invoked method - deleteAd");
        imageService.deleteAdImage(adId);
        adsRepository.deleteById(adId);
    }

    /**
     * Метод принимает id объявления и его актуальные данные, ищет сущность {@link Ads} в БД,
     * если объявление не найдено - возвращает {@code null},
     * если найдено - изменяет данные на актуальные и возвращает новые данные объявления в виде DTO.
     *
     * @param adId      идентификатор объявления
     * @param newAdData актуальные данные объявления
     * @return {@link AdsDto} / {@code null}
     */
    @Override
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

        return adsMapper.adsToAdsDto(updatedAd);
    }

    /**
     * Метод получает список объявлений авторизованного пользователя из {@link AdsRepository},
     * конвертирует полученный список и отдает результат в виде DTO.
     *
     * @return {@link ResponseWrapperAdsDto}
     */
    @Override
    public ResponseWrapperAdsDto getMyAds() {
        log.info("Was invoked method - getMyAds");
        return responseWrapperAdsDtoMapper.toResponseWrapperAdsDto(adsRepository.findAllByAuthor(getAuthUser()));
    }

    /**
     * Метод принимает id объявления и его новую картинку, ищет сущность {@link Ads} в БД,
     * если объявление не найдено - выбрасывает исключение {@link AdNotFoundException},
     * если найдено - удаляет старый файл картинки, сохраняет и присваивает новую картинку объявлению,
     * возвращает ссылку на эндпоинт для получения картинки.
     *
     * @param adId    идентификатор объявления
     * @param adImage новая картинка объявления
     * @return эндпоинт для получения картинки
     */
    @Override
    public String updateAdImage(int adId, MultipartFile adImage) throws IOException, AdNotFoundException {
        log.info("Was invoked method - updateAdImage");

        Ads oldAdData = adsRepository.findById(adId);
        if (oldAdData == null) {
            throw new AdNotFoundException("Ad don't exist");
        }

        imageService.deleteAdImage(adId);

        oldAdData.setImage(imageService.saveImage(oldAdData, adImage));
        Ads updatedAd = adsRepository.save(oldAdData);

        return updatedAd.getImage().getImageApi();
    }

    /**
     * Метод возвращает авторизованного пользователя.
     *
     * @return {@link User}
     */
    private User getAuthUser() {
        return userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
    }
}
