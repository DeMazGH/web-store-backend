package ru.skypro.homework.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AuthValidator {

    public boolean userIsNotAuthorised() {
        log.info("Was invoked method - userIsNotAuthorised");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth == null;
    }
}
