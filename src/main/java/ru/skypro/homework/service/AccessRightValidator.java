package ru.skypro.homework.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.Role;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.repository.AdsRepository;
import ru.skypro.homework.repository.CommentRepository;
import ru.skypro.homework.repository.UserRepository;

import java.util.Objects;

@Slf4j
@Service
public class AccessRightValidator {

    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final AdsRepository adsRepository;

    public AccessRightValidator(UserRepository userRepository, CommentRepository commentRepository, AdsRepository adsRepository) {
        this.userRepository = userRepository;
        this.commentRepository = commentRepository;
        this.adsRepository = adsRepository;
    }

    public boolean userHaveAccessToComment(int commentId) {
        log.info("Was invoked method - userHaveAccessToComment");

        User currentUser = getAuthUser();
        if (currentUser == null) {
            return false;
        }
        if (userIsAdmin(currentUser)) {
            return true;
        }

        User userWhoCommented = commentRepository.findById(commentId).getUser();
        if (userWhoCommented == null) {
            return false;
        } else {
            return Objects.equals(currentUser.getId(), userWhoCommented.getId());
        }
    }

    public boolean userHaveAccessToAd(int adId) {
        log.info("Was invoked method - userHaveAccessToAd");

        User currentUser = getAuthUser();
        if (currentUser == null) {
            return false;
        }
        if (userIsAdmin(currentUser)) {
            return true;
        }

        User userWhoCommented = adsRepository.findById(adId).getAuthor();
        if (userWhoCommented == null) {
            return false;
        } else {
            return Objects.equals(currentUser.getId(), userWhoCommented.getId());
        }
    }

    private User getAuthUser() {
        return userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
    }

    private boolean userIsAdmin(User currentUser) {
            return currentUser.getRole() == Role.ADMIN;
    }
}
