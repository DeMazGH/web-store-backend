package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.Role;
import ru.skypro.homework.entity.Ads;
import ru.skypro.homework.entity.Comment;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.exception.AdNotFoundException;
import ru.skypro.homework.exception.CommentNotFoundException;
import ru.skypro.homework.repository.AdsRepository;
import ru.skypro.homework.repository.CommentRepository;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.AccessRightValidator;

import java.util.Objects;

/**
 * Сервис для проверки прав авторизованного пользователя.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AccessRightValidatorImpl implements AccessRightValidator {

    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final AdsRepository adsRepository;

    /**
     * Метод проверяет есть ли у пользователя право доступа для изменения комментария.
     *
     * @param commentId идентификатор комментария
     * @return {@code true} - если есть права, {@code false} - если прав нет
     * @throws CommentNotFoundException если комментария с данным id не существует
     */
    @Override
    public boolean userHaveAccessToComment(int commentId) throws CommentNotFoundException {
        log.info("Was invoked method - userHaveAccessToComment");

        User currentUser = getAuthUser();
        if (currentUser == null) {
            return false;
        }
        if (userIsAdmin(currentUser)) {
            return true;
        }

        Comment comment = commentRepository.findById(commentId);
        if (comment == null) {
            throw new CommentNotFoundException("Comment doesn't exist");
        }

        User userWhoCommented = comment.getUser();
        if (userWhoCommented == null) {
            return false;
        } else {
            return Objects.equals(currentUser.getId(), userWhoCommented.getId());
        }
    }

    /**
     * Метод проверяет есть ли у пользователя право доступа для изменения объявления.
     *
     * @param adId идентификатор объявления
     * @return {@code true} - если есть права, {@code false} - если прав нет
     * @throws AdNotFoundException если объявления с данным id не существует
     */
    @Override
    public boolean userHaveAccessToAd(int adId) throws AdNotFoundException {
        log.info("Was invoked method - userHaveAccessToAd");

        User currentUser = getAuthUser();
        if (currentUser == null) {
            return false;
        }
        if (userIsAdmin(currentUser)) {
            return true;
        }

        Ads ad = adsRepository.findById(adId);
        if (ad == null) {
            throw new AdNotFoundException("Ad doesn't exist");
        }

        User userWhoCommented = ad.getAuthor();
        if (userWhoCommented == null) {
            return false;
        } else {
            return Objects.equals(currentUser.getId(), userWhoCommented.getId());
        }
    }

    /**
     * Метод возвращает авторизованного пользователя.
     *
     * @return {@link User}
     */
    private User getAuthUser() {
        return userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
    }

    /**
     * Метод проверяет - является ли пользователь администратором.
     *
     * @param currentUser пользователь проходящий проверку
     * @return {@code true} - если пользователь является администратором, {@code false} - если не является
     */
    private boolean userIsAdmin(User currentUser) {
        return currentUser.getRole() == Role.ADMIN;
    }
}
