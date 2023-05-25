package ru.skypro.homework.service;

import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.entity.Ads;
import ru.skypro.homework.entity.Avatar;
import ru.skypro.homework.entity.Image;
import ru.skypro.homework.entity.User;

import java.io.IOException;
import java.nio.file.Path;

public interface ImageService {

    Image saveImage(Ads ad, MultipartFile adImage) throws IOException;

    Avatar saveAvatar(User currentUser, MultipartFile avatarImage) throws IOException;

    Path getImagePath(int adId);

    Path getAvatarPath(int userId);

    void deleteAdImage(int adId) throws IOException;

    void deleteAvatar(int userId) throws IOException;
}
