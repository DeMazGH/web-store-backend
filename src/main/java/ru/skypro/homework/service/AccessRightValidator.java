package ru.skypro.homework.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.Role;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.repository.CommentRepository;
import ru.skypro.homework.repository.UserRepository;

import java.util.Objects;

@Slf4j
@Service
public class AccessRightValidator {

    UserRepository userRepository;
    CommentRepository commentRepository;

    public AccessRightValidator(UserRepository userRepository, CommentRepository commentRepository) {
        this.userRepository = userRepository;
        this.commentRepository = commentRepository;
    }

    public boolean userHaveAccess(int commentId) {
        log.info("Was invoked method - userDontHaveAccess");

        User currentUser = userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        if (currentUser == null) {
            return false;
        } else if (currentUser.getRole() == Role.ADMIN) {
            return true;
        }

        User userWhoCommented = commentRepository.findById(commentId).getUser();
        if (userWhoCommented == null) {
            return false;
        } else {
            return Objects.equals(currentUser.getId(), userWhoCommented.getId());
        }
    }
}
