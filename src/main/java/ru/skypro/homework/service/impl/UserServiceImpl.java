package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.NewPasswordDto;
import ru.skypro.homework.dto.UserDto;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.mapper.UserMapper;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.UserService;

import java.io.IOException;

/**
 * Сервис для работы с сущностью {@link User}.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ImageServiceImpl imageService;
    private final PasswordEncoder encoder;
    private final UserMapper userMapper;

    /**
     * Метод принимает данные в виде DTO о текущем и новом паролях,
     * производит проверку совпадения текущего пароля с переданным в DTO.
     * Если все проверка пройдена успешно, то меняет пароль на новый и сохраняет изменения в БД.
     * Возвращает булево значение соответствующее успешности изменения пароля.
     *
     * @param newPasswordDto данные о текущем и новом паролях
     * @return {@code true} - пароль успешно изменен, {@code true} - отказано в доступе
     */
    @Override
    public boolean setPassword(NewPasswordDto newPasswordDto) {
        log.debug("Was invoked method - setPassword");
        User currentUser = getAuthUser();
        if (!encoder.matches(newPasswordDto.getCurrentPassword(), currentUser.getPassword())) {
            return false;
        } else {
            currentUser.setPassword(encoder.encode(newPasswordDto.getNewPassword()));
            userRepository.save(currentUser);
            return true;
        }
    }

    /**
     * Метод возвращает данные об авторизованном пользователе.
     *
     * @return {@link UserDto}
     */
    @Override
    public UserDto getMe() {
        log.debug("Was invoked method - getMe");
        User currentUser = getAuthUser();
        return userMapper.userToUserDto(currentUser);
    }

    /**
     * Метод принимает новые данные пользователя, далее получает авторизованного пользователя из БД,
     * изменяет данные на актуальные и возвращает новые данные пользователя в виде DTO.
     *
     * @param userDto новые данные пользователя
     * @return {@link UserDto}
     */
    @Override
    public UserDto updateUser(UserDto userDto) {
        log.debug("Was invoked method - updateUser");
        User oldUserData = getAuthUser();

        oldUserData.setFirstName(userDto.getFirstName());
        oldUserData.setLastName(userDto.getLastName());
        oldUserData.setPhone(userDto.getPhone());

        User newUserData = userRepository.save(oldUserData);

        return userMapper.userToUserDto(newUserData);
    }

    /**
     * Метод принимает картинку, далее получает авторизованного пользователя из БД,
     * удаляет старую картинку, сохраняет новую картинку в сущность пользователя и сохраняет изменения в БД.
     *
     * @param avatar аватар пользователя
     */
    @Override
    public void updateImage(MultipartFile avatar) throws IOException {
        log.debug("Was invoked method - updateImage");

        User currentUser = getAuthUser();
        imageService.deleteAvatar(currentUser.getId());
        currentUser.setAvatar(imageService.saveAvatar(currentUser, avatar));
        userRepository.save(currentUser);
    }

    /**
     * Метод возвращает авторизованного пользователя.
     *
     * @return авторизованный пользователь
     */
    private User getAuthUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return userRepository.findByEmail(authentication.getName());
    }
}
