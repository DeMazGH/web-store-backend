package ru.skypro.homework.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.UserDto;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.mapper.UserMapper;
import ru.skypro.homework.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDto getMe() {
        User currentUser = userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        if (currentUser == null) {
            return null;
        } else {
            return UserMapper.INSTANCE.userToUserDto(currentUser);
        }
    }

    public boolean authorizationCheck() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth != null;
    }

    public UserDto updateUser(UserDto userDto) {
        User newUserData = UserMapper.INSTANCE.userDtoToUser(userDto);
        userRepository.save(newUserData);
        return userDto;
    }

    public boolean dataIsNew(UserDto userDto) {
        User newUserData = UserMapper.INSTANCE.userDtoToUser(userDto);
        User currentUserData = userRepository.findById(newUserData.getId()).get();
        return !newUserData.equals(currentUserData);
    }

//    public void updateImage(MultipartFile image) {
//        User currentUser = userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
//
//
//
//        currentUser.setImage();
//    }
}
