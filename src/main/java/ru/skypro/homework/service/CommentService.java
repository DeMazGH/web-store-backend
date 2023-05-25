package ru.skypro.homework.service;

import ru.skypro.homework.dto.CommentDto;
import ru.skypro.homework.dto.CreateCommentDto;
import ru.skypro.homework.dto.ResponseWrapperCommentDto;

public interface CommentService {

    ResponseWrapperCommentDto getAdComments(int adId);

    CommentDto addCommentToAd(int adId, CreateCommentDto createdComment);

    void deleteAdsComment(int commentId);

    CommentDto updateAdComment(int commentId, CommentDto newData);

    boolean dataIsConsistent(int adId, int commentId);
}
