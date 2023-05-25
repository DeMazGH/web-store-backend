package ru.skypro.homework.service;

public interface AccessRightValidator {

    boolean userHaveAccessToComment(int commentId);

    boolean userHaveAccessToAd(int adId);
}
