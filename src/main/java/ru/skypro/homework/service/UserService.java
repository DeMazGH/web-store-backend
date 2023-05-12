package ru.skypro.homework.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.NewPasswordDto;
import ru.skypro.homework.dto.UserDto;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.mapper.UserMapper;
import ru.skypro.homework.repository.UserRepository;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

import static java.nio.file.StandardOpenOption.CREATE_NEW;

/**
 * Сервис для работы с сущностью {@link User}.
 */
@Slf4j
@Service
public class UserService {

    @Value("${images.dir.path}")
    private String imageDir;

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Метод принимает данные в виде DTO о текущем и новом паролях,
     * производит проверки: авторизации, совпадения текущего пароля с переданным в DTO.
     * Если все проверки пройдены успешно, то меняет пароль на новый и сохраняет изменения в БД.
     * Возвращает булево значение соответствующее успешности изменения пароля.
     * @param newPasswordDto данные о текущем и новом паролях
     * @return {@code true} - пароль успешно изменен, {@code true} - отказано в доступе
     */
    public boolean setPassword(NewPasswordDto newPasswordDto) {
        log.info("Was invoked method - setPassword");
        User currentUser = getAuthUser();
        //нужна ли, здесь и ниже, проверка на null после получения авторизованного пользователя из БД?
        if (currentUser == null) {
            return false;
        } else if (!currentUser.getPassword().equals(newPasswordDto.getCurrentPassword())) {
            return false;
        } else {
            currentUser.setPassword(newPasswordDto.getNewPassword());
            userRepository.save(currentUser);
            return true;
        }
    }

    /**
     * Метод проверяет наличие пользователя в БД.
     * Если пользователь не найдено - возвращает {@code null}, если найден возвращает данные о пользователе.
     * @return {@link UserDto} / {@code null}
     */
    public UserDto getMe() {
        log.info("Was invoked method - getMe");
        User currentUser = getAuthUser();
        return (currentUser == null) ? null : UserMapper.INSTANCE.userToUserDto(currentUser);
    }

    /**
     * Метод принимает новые данные пользователя, далее получает авторизованного пользователя из БД.
     * Если пользователь не найден - возвращает {@code null},
     * если найден - изменяет данные на актуальные и возвращает новые данные пользователя в виде DTO.
     * @param userDto новые данные пользователя
     * @return {@link UserDto} / {@code null}
     */
    public UserDto updateUser(UserDto userDto) {
        log.info("Was invoked method - updateUser");
        User oldUserData = getAuthUser();
        if (oldUserData == null) {
            return null;
        }

        oldUserData.setFirstName(userDto.getFirstName());
        oldUserData.setLastName(userDto.getLastName());
        oldUserData.setPhone(userDto.getPhone());
        //Здесь нужно менять поля email? image?
        //для смены image у нас есть отдельный метод
        //я так думаю для смены логина(email) тоже лучше сделать отдельный метод

        //id переданный в DTO игнорируем?
        // или нужно сравнить с авторизованным пользователем и если не совпадает прервать выполнение метода?

        User newUserData =  userRepository.save(oldUserData);
        return UserMapper.INSTANCE.userToUserDto(newUserData);
    }

    /**
     * Метод принимает картинку, далее получает авторизованного пользователя из БД.
     * Если пользователь не найден - возвращает {@code null},
     * если найден - создает путь файла для картинки, директорию, удаляет старую картинку, создает файл
     * и копирует картинку, устанавливает значение пути картинки у текущего пользователя и сохраняет изменения в БД.
     * @param image аватар пользователя
     * *@throws IOException
     */
    public void updateImage(MultipartFile image) throws IOException {
        log.info("Was invoked method - updateImage");

        User currentUser = getAuthUser();
        if (currentUser == null) {
            log.error("User not found");
            return;
        }

        Path filePath = Path.of(imageDir, currentUser.getEmail() + "."
                + getExtensions(Objects.requireNonNull(image.getOriginalFilename())));
        Files.createDirectories(filePath.getParent());
        Files.deleteIfExists(filePath);

        try (
                InputStream is = image.getInputStream();
                OutputStream os = Files.newOutputStream(filePath, CREATE_NEW);
                BufferedInputStream bis = new BufferedInputStream(is, 1024);
                BufferedOutputStream bos = new BufferedOutputStream(os, 1024)
        ) {
            bis.transferTo(bos);
        }

        currentUser.setImage(filePath.toString());
        userRepository.save(currentUser);
    }

    /**
     * Метод получает строку с именем файла, извлекает и возвращает расширение этого файла.
     * @param fileName имя файла
     * @return расширение этого файла
     */
    private String getExtensions(String fileName) {
        log.info("Was invoked method - getExtensions");
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    /**
     * Метод возвращает авторизованного пользователя.
     * @return авторизованный пользователь
     */
    private User getAuthUser() {
        return userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
    }
}
