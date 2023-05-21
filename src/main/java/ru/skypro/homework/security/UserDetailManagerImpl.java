package ru.skypro.homework.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.repository.UserRepository;

@Slf4j
@Service
public class UserDetailManagerImpl implements UserDetailsManager {

    private final UserRepository userRepository;
    private final SecurityUser securityUser;

    public UserDetailManagerImpl(UserRepository userRepository, SecurityUser securityUser) {
        this.userRepository = userRepository;
        this.securityUser = securityUser;
    }

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

    @Override
    public boolean userExists(String email) {
        log.info("Was invoked method - userExists");
        return userRepository.findByEmail(email) != null;
    }

    /**
     * Метод из БД получает пользователя по его имени, если пользователь не существует - выкидывает исключение,
     * если существует - возвращает его в виде сущности {@link SecurityUser}.
     *
     * @param email имя пользователя
     * @return {@link SecurityUser}
     * @throws UsernameNotFoundException если пользователь не найден в БД
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        log.info("Was invoked method - loadUserByUsername");
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("User doesn't exists");
        }
        securityUser.setUser(user);
        return securityUser;
    }
}
