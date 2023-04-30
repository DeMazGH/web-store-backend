package ru.skypro.homework.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
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

@Slf4j
@Service
public class UserService {

    @Value("${images.dir.path}")
    private String imageDir;

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean setPassword(NewPasswordDto newPasswordDto) {
        log.info("Was invoked method - setPassword");
        User currentUser = userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
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

    public UserDto getMe() {
        log.info("Was invoked method - getMe");
        User currentUser = userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        if (currentUser == null) {
            return null;
        } else {
            return UserMapper.INSTANCE.userToUserDto(currentUser);
        }
    }

    public boolean userIsNotAuthorised() {
        log.info("Was invoked method - userIsNotAuthorised");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth == null;
    }

    public UserDto updateUser(UserDto userDto) {
        log.info("Was invoked method - updateUser");
        User newUserData = UserMapper.INSTANCE.userDtoToUser(userDto);
        userRepository.save(newUserData);
        return userDto;
    }

    public void updateImage(MultipartFile image) throws IOException {
        log.info("Was invoked method - updateImage");
        User currentUser = userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
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

    private String getExtensions(String fileName) {
        log.info("Was invoked method - getExtensions");
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }
}
