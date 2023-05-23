package ru.skypro.homework.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.entity.Ads;
import ru.skypro.homework.entity.Image;
import ru.skypro.homework.repository.ImageRepository;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

import static java.nio.file.StandardOpenOption.CREATE_NEW;

@Slf4j
@Service
@RequiredArgsConstructor
public class ImageService {

    private final ImageRepository imageRepository;

    @Value("${ads.image.dir.path}")
    private String imageDir;

    public Image saveImageAsFile(Ads ad, MultipartFile adImage) throws IOException {
        log.info("Was invoked method - updateImage");

        Path filePath = Path.of(imageDir, ad.getId() + "."
                + getExtensions(Objects.requireNonNull(adImage.getOriginalFilename())));
        Files.createDirectories(filePath.getParent());
        Files.deleteIfExists(filePath);

        try (
                InputStream is = adImage.getInputStream();
                OutputStream os = Files.newOutputStream(filePath, CREATE_NEW);
                BufferedInputStream bis = new BufferedInputStream(is, 1024);
                BufferedOutputStream bos = new BufferedOutputStream(os, 1024)
        ) {
            bis.transferTo(bos);
        }

        Image image = new Image();
        image.setFilePath(filePath.toString());
        image.setAd(ad);

        return imageRepository.save(image);
    }

    public Path getImagePath(int adId) {
        log.info("Was invoked method - getImagePath");

        Image image = imageRepository.findImageByAdId(adId);

        if (null == image) {
            throw new RuntimeException("Image doesn't exist");
        }
        return Path.of(image.getFilePath());
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
}
