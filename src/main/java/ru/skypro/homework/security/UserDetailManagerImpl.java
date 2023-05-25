package ru.skypro.homework.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.exception.UserNotFoundException;
import ru.skypro.homework.repository.UserRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserDetailManagerImpl implements UserDetailsManager {

    private final UserRepository userRepository;
    private final SecurityUser securityUser;

    @Override
    public void createUser(UserDetails user) {
    }

    @Override
    public void updateUser(UserDetails user) {
    }

    @Override
    public void deleteUser(String username) {
    }

    @Override
    public void changePassword(String oldPassword, String newPassword) {
    }

    /**
     * Метод ищет пользователя в БД по его логину,
     * возвращает {@code true} если пользователь есть в БД, {@code false} если пользователя нет в БД.
     *
     * @param email логин пользователя
     * @return {@code true}/{@code false}
     */
    @Override
    public boolean userExists(String email) {
        log.info("Was invoked method - userExists");
        return userRepository.findByEmail(email) != null;
    }

    /**
     * Метод из БД получает пользователя по его логину, если пользователь не существует - выкидывает исключение,
     * если существует - возвращает его в виде сущности {@link SecurityUser}.
     *
     * @param email логин пользователя
     * @return {@link SecurityUser}
     * @throws UserNotFoundException если пользователь не найден в БД
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UserNotFoundException {
        log.info("Was invoked method - loadUserByUsername");
        User user = userRepository.findByEmail(email);
        if (null == user) {
            throw new UserNotFoundException("User doesn't exists");
        }
        securityUser.setUser(user);
        return securityUser;
    }
}
