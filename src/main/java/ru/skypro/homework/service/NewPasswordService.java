package ru.skypro.homework.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.NewPasswordDto;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.repository.UserRepository;

@Service
public class NewPasswordService {
    private final UserRepository userRepository;

    public NewPasswordService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public ResponseEntity<?> setPassword(NewPasswordDto newPasswordDto) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        User currentUser = userRepository.findByEmail(auth.getName());

        if (currentUser == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } else if (!currentUser.getPassword().equals(newPasswordDto.getCurrentPassword())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        } else {
            currentUser.setPassword(newPasswordDto.getNewPassword());
            userRepository.save(currentUser);
            return ResponseEntity.ok().build();
        }
    }
}
