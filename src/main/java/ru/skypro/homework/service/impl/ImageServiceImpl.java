package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.entity.Ads;
import ru.skypro.homework.entity.Avatar;
import ru.skypro.homework.entity.Image;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.repository.AvatarRepository;
import ru.skypro.homework.repository.ImageRepository;
import ru.skypro.homework.service.ImageService;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

import static java.nio.file.StandardOpenOption.CREATE_NEW;

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

    @Override
    public Image saveImageAsFile(Ads ad, MultipartFile adImage) throws IOException {
        log.info("Was invoked method - updateImage");

        Path filePath = Path.of(imageDir, ad.getId() + "."
                + getExtensions(Objects.requireNonNull(adImage.getOriginalFilename())));
        copyImageAlongPath(adImage, filePath);

        Image image = imageRepository.findImageByAd(ad).orElse(new Image());
        image.setFilePath(filePath.toString());
        image.setAd(ad);

        return imageRepository.save(image);
    }

    /**
     * Метод принимает картинку, далее получает авторизованного пользователя из БД,
     * создает путь файла для картинки, директорию, удаляет старую картинку, создает файл
     * и копирует картинку, устанавливает значение пути картинки у текущего пользователя и сохраняет изменения в БД.
     *
     * @param avatarImage аватар пользователя
     *                    *@throws IOException
     */
    @Override
    public Avatar saveAvatarAsFile(User currentUser, MultipartFile avatarImage) throws IOException {
        log.info("Was invoked method - updateImage");

        Path filePath = Path.of(avatarDir, currentUser.getEmail() + "."
                + getExtensions(Objects.requireNonNull(avatarImage.getOriginalFilename())));
        copyImageAlongPath(avatarImage, filePath);

        Avatar avatar = avatarRepository.findByUser(currentUser).orElse(new Avatar());
        avatar.setFilePath(filePath.toString());
        avatar.setUser(currentUser);

        return avatarRepository.save(avatar);
    }

    @Override
    public Path getImagePath(int adId) {
        log.info("Was invoked method - getImagePath");
        Image image = imageRepository.findImageByAdId(adId);
        if (null == image) {
            throw new RuntimeException("Image doesn't exist");
        }
        return Path.of(image.getFilePath());
    }

    @Override
    public Path getAvatarPath(int userId) {
        log.info("Was invoked method - getAvatarPath");
        Avatar avatar = avatarRepository.findByUserId(userId);
        if (null == avatar) {
            throw new RuntimeException("Avatar doesn't exist");
        }
        return Path.of(avatar.getFilePath());
    }

    @Override
    public void deleteAdImage(int adId) throws IOException {
        Image adImage = imageRepository.findImageByAdId(adId);
        if (null == adImage) {
            return;
        }
        Files.deleteIfExists(Path.of(adImage.getFilePath()));
    }

    @Override
    public void deleteAvatar(int userId) throws IOException {
        Avatar avatar = avatarRepository.findByUserId(userId);
        if (null == avatar) {
            return;
        }
        Files.deleteIfExists(Path.of(avatar.getFilePath()));
    }

    /**
     * Метод получает строку с именем файла, извлекает и возвращает расширение этого файла.
     *
     * @param fileName имя файла
     * @return расширение этого файла
     */
    private String getExtensions(String fileName) {
        log.info("Was invoked method - getExtensions");
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

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
