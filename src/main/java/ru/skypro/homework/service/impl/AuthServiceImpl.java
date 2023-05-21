package ru.skypro.homework.service.impl;

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

@Service
public class AuthServiceImpl implements AuthService {

    private final UserDetailManagerImpl manager;
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;

    public AuthServiceImpl(UserDetailManagerImpl manager, UserRepository userRepository, PasswordEncoder encoder) {
        this.manager = manager;
        this.userRepository = userRepository;
        this.encoder = encoder;
    }

    @Override
    public boolean login(String userName, String password) {
        if (!manager.userExists(userName)) {
            return false;
        }
        UserDetails userDetails = manager.loadUserByUsername(userName);
        return encoder.matches(password, userDetails.getPassword());
    }

    @Override
    public boolean register(RegisterReq registerReq, Role role) {
        if (manager.userExists(registerReq.getUsername())) {
            return false;
        }
        saveUserInDb(registerReq, role);
        return true;
    }

    private void saveUserInDb(RegisterReq registerReq, Role role) {
        User newUser = UserMapper.INSTANCE.registerReqToUser(registerReq);
        newUser.setPassword(encoder.encode(registerReq.getPassword()));
        newUser.setRole(role);
        userRepository.save(newUser);
    }
}
