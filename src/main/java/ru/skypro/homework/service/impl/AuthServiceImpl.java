package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.RegisterReq;
import ru.skypro.homework.dto.Role;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.mapper.UserMapper;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.security.UserDetailManagerImpl;
import ru.skypro.homework.service.AuthService;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserDetailManagerImpl manager;
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final UserMapper userMapper;

    /**
     * Метод проверяет есть ли пользователь в БД, если пользователь не найден в БД возвращает {@code false},
     * если пользователь найден получает его из БД, возвращает результат сравнения пароля переданного
     * в параметрах метода и пароля пользователя, используя {@link PasswordEncoder}.
     *
     * @param userName имя пользователя
     * @param password пароль введенный пользователем
     * @return {@code true}/{@code false}
     */
    @Override
    public boolean login(String userName, String password) {
        log.debug("Was invoked method - login");
        if (!manager.userExists(userName)) {
            return false;
        }
        UserDetails userDetails = manager.loadUserByUsername(userName);
        return encoder.matches(password, userDetails.getPassword());
    }

    /**
     * Метод проверяет есть ли пользователь в БД, если пользователь не найден в БД возвращает {@code false},
     * если пользователь найден, сохраняет его в БД и возвращает {@code true}.
     *
     * @param registerData данные для регистрации пользователя в виде DTO
     * @param role         роль пользователя {@link Role}
     * @return {@code true}/{@code false}
     */
    @Override
    public boolean register(RegisterReq registerData, Role role) {
        log.debug("Was invoked method - register");
        if (manager.userExists(registerData.getUsername())) {
            return false;
        }
        saveUserInDb(registerData, role);
        return true;
    }

    /**
     * Метод создает сущность {@link User} на основании регистрационных данных,
     * устанавливает пароль с помощью {@link PasswordEncoder}, устанавливает переданную роль,
     * сохраняет пользователя в БД.
     *
     * @param registerData данные для регистрации пользователя в виде DTO
     * @param role         роль пользователя
     */
    private void saveUserInDb(RegisterReq registerData, Role role) {
        log.debug("Was invoked method - saveUserInDb");
        User newUser = userMapper.registerReqToUser(registerData);
        newUser.setPassword(encoder.encode(registerData.getPassword()));
        newUser.setRole(role);
        userRepository.save(newUser);
    }
}
