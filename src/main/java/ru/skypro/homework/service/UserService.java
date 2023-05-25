package ru.skypro.homework.service;

import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.NewPasswordDto;
import ru.skypro.homework.dto.UserDto;

import java.io.IOException;

public interface UserService {

    boolean setPassword(NewPasswordDto newPasswordDto);

    UserDto getMe();

    UserDto updateUser(UserDto userDto);

    void updateImage(MultipartFile avatar) throws IOException;
}
