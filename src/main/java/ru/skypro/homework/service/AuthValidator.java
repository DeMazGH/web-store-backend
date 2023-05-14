package ru.skypro.homework.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AuthValidator {

    //Дима написал что по идее этот метод не нужен и что у нас в WebSecurityConfig закрыты эндпоинты /ads/** и /users/**
    //Пока не понял как это работает, до конц SpringSecurity не было поэтому пока оставлю этот метод
    public boolean userIsNotAuthorised() {
        log.info("Was invoked method - userIsNotAuthorised");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth == null;
    }
}
