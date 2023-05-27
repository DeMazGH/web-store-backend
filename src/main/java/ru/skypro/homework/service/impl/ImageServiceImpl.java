package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.entity.Ads;
import ru.skypro.homework.entity.Avatar;
import ru.skypro.homework.entity.Image;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.exception.AvatarNotFoundException;
import ru.skypro.homework.exception.ImageNotFoundException;
import ru.skypro.homework.repository.AvatarRepository;
import ru.skypro.homework.repository.ImageRepository;
import ru.skypro.homework.service.ImageService;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

import static java.nio.file.StandardOpenOption.CREATE_NEW;

/**
 * Сервис для работы с изображениями (сущности {@link Avatar} и {@link Image})
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {

    private final ImageRepository imageRepository;
    private final AvatarRepository avatarRepository;

    @Value("${ads.image.dir.path}")
    private String imageDir;

    @Value("${user.avatar.dir.path}")
    private String avatarDir;

    /**
     * Метод создает путь по которому сохранит файл изображения, копирует переданный файл по этому пути.
     * Далее ищет картинку в БД, если сущность {@link Image} не найдена - создает новую,
     * устанавливает новые значения полей сущности и сохраняет ее в БД.
     *
     * @param ad      объявление для которого сохраняется картинка
     * @param adImage картинка для объявления
     * @return {@link Image}
     */
    @Override
    public Image saveImage(Ads ad, MultipartFile adImage) throws IOException {
        log.debug("Was invoked method - saveImage");

        Path filePath = Path.of(imageDir, ad.getId() + "."
                + StringUtils.getFilenameExtension(adImage.getOriginalFilename()));
        copyImageAlongPath(adImage, filePath);

        Image image = imageRepository.findImageByAd(ad).orElse(new Image());
        image.setFilePath(filePath.toString());
        image.setAd(ad);

        return imageRepository.save(image);
    }

    /**
     * Метод создает путь по которому сохранит файл изображения, копирует переданный файл по этому пути.
     * Далее ищет аватар в БД, если сущность {@link Avatar} не найдена - создает новую,
     * устанавливает новые значения полей сущности и сохраняет ее в БД.
     *
     * @param currentUser пользователь для которого сохраняется картинка
     * @param avatarImage аватар пользователя
     * @return {@link Avatar}
     */
    @Override
    public Avatar saveAvatar(User currentUser, MultipartFile avatarImage) throws IOException {
        log.debug("Was invoked method - updateImage");

        Path filePath = Path.of(avatarDir, currentUser.getEmail() + "."
                + StringUtils.getFilenameExtension(avatarImage.getOriginalFilename()));
        copyImageAlongPath(avatarImage, filePath);

        Avatar avatar = avatarRepository.findByUser(currentUser).orElse(new Avatar());
        avatar.setFilePath(filePath.toString());
        avatar.setUser(currentUser);

        return avatarRepository.save(avatar);
    }

    /**
     * Метод получает картинку из репозитория по id объявления,
     * проверяет - существует ли она, если нет выбрасывает исключение,
     * если картинка найдена возвращает путь к изображению.
     *
     * @param adId id объявления
     * @return {@link Path}
     */
    @Override
    public Path getImagePath(int adId) throws ImageNotFoundException {
        log.debug("Was invoked method - getImagePath");
        Image image = imageRepository.findImageByAdId(adId);
        if (image == null) {
            throw new ImageNotFoundException("Image doesn't exist");
        }
        return Path.of(image.getFilePath());
    }

    /**
     * Метод получает аватар из репозитория по id пользователя,
     * проверяет - существует ли он, если нет выбрасывает исключение,
     * если аватар найден возвращает путь к изображению.
     *
     * @param userId id пользователя
     * @return {@link Path}
     */
    @Override
    public Path getAvatarPath(int userId) throws AvatarNotFoundException {
        log.debug("Was invoked method - getAvatarPath");
        Avatar avatar = avatarRepository.findByUserId(userId);
        if (avatar == null) {
            throw new AvatarNotFoundException("Avatar doesn't exist");
        }
        return Path.of(avatar.getFilePath());
    }

    /**
     * Метод получает картинку из репозитория по id объявления,
     * проверяет - существует ли она, если нет выбрасывает исключение,
     * если картинка найдена удаляет ее из файловой системы.
     *
     * @param adId id объявления
     */
    @Override
    public void deleteAdImage(int adId) throws IOException {
        Image adImage = imageRepository.findImageByAdId(adId);
        if (adImage == null) {
            return;
        }
        Files.deleteIfExists(Path.of(adImage.getFilePath()));
    }

    /**
     * Метод получает аватар из репозитория по id пользователя,
     * проверяет - существует ли он, если нет выбрасывает исключение,
     * если аватар найден удаляет его из файловой системы.
     *
     * @param userId id пользователя
     */
    @Override
    public void deleteAvatar(int userId) throws IOException {
        Avatar avatar = avatarRepository.findByUserId(userId);
        if (avatar == null) {
            return;
        }
        Files.deleteIfExists(Path.of(avatar.getFilePath()));
    }

    /**
     * Метод принимает изображение и путь по которому его нужно сохранить,
     * создает директорию, и копирует в нее картинку.
     *
     * @param image    копируемое изображение
     * @param filePath путь сохранения изображения
     */
    private void copyImageAlongPath(MultipartFile image, Path filePath) throws IOException {
        Files.createDirectories(filePath.getParent());
        try (
                InputStream is = image.getInputStream();
                OutputStream os = Files.newOutputStream(filePath, CREATE_NEW);
                BufferedInputStream bis = new BufferedInputStream(is, 1024);
                BufferedOutputStream bos = new BufferedOutputStream(os, 1024)
        ) {
            bis.transferTo(bos);
        }
    }
}
