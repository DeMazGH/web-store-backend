package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.CommentDto;
import ru.skypro.homework.dto.CreateCommentDto;
import ru.skypro.homework.dto.ResponseWrapperCommentDto;
import ru.skypro.homework.entity.Comment;
import ru.skypro.homework.mapper.CommentMapper;
import ru.skypro.homework.mapper.ResponseWrapperCommentDtoMapper;
import ru.skypro.homework.repository.AdsRepository;
import ru.skypro.homework.repository.CommentRepository;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.CommentService;

import java.time.LocalDateTime;

/**
 * Сервис для работы с сущностью {@link Comment}.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final AdsRepository adsRepository;
    private final UserRepository userRepository;

    /**
     * Метод принимает id объявления, получает список всех комментариев к данному объявлению и возвращает ответ в виде DTO.
     *
     * @param adId идентификатор объявления
     * @return {@link ResponseWrapperCommentDto}
     */
    @Override
    public ResponseWrapperCommentDto getAdComments(int adId) {
        log.info("Was invoked method - getAdComments");
        return ResponseWrapperCommentDtoMapper.INSTANCE.toResponseWrapperCommentDto(commentRepository.findByAds_Id(adId));
    }

    /**
     * Метод принимает id объявления для которого создается комментарий и необходимые данные для создания комментария,
     * в виде DTO. На их основе создает сущность {@link Comment} и сохраняет ее в БД.
     * После сохранения возвращает актуальные данные о созданном комментарии в виде DTO.
     *
     * @param adId           идентификатор объявления для которого создается комментарий
     * @param createdComment необходимые данные для создания комментария
     * @return {@link CommentDto}
     */
    @Override
    public CommentDto addCommentToAd(int adId, CreateCommentDto createdComment) {
        log.info("Was invoked method - addCommentToAd");

        Comment newComment = CommentMapper.INSTANCE.createCommentDtoToComment(createdComment);
        newComment.setCreatedAt(LocalDateTime.now());
        newComment.setAds(adsRepository.getReferenceById(adId));
        newComment.setUser(userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName()));

        Comment savedComment = commentRepository.save(newComment);

        return CommentMapper.INSTANCE.commentToCommentDto(savedComment);
    }

    /**
     * Метод принимает id комментария, по id удаляет сущность {@link Comment} из БД.
     *
     * @param commentId идентификатор комментария
     */
    @Override
    public void deleteAdsComment(int commentId) {
        log.info("Was invoked method - deleteAdsComment");
        commentRepository.deleteById(commentId);
    }

    /**
     * Метод принимает id комментария и его актуальные данные, ищет сущность {@link Comment} в БД,
     * если комментарий не найден - возвращает {@code null},
     * если найден - изменяет данные на актуальные и возвращает новые данные комментария в виде DTO.
     *
     * @param commentId идентификатор комментария
     * @param newData   новые данные для изменения комментария
     * @return {@link CommentDto} / {@code null}
     */
    @Override
    public CommentDto updateAdComment(int commentId, CommentDto newData) {
        log.info("Was invoked method - updateAdComment");

        Comment oldCommentData = commentRepository.findById(commentId);
        if (oldCommentData == null) {
            return null;
        }

        oldCommentData.setText(newData.getText());

        Comment updatedComment = commentRepository.save(oldCommentData);

        return CommentMapper.INSTANCE.commentToCommentDto(updatedComment);
    }

    /**
     * Метод по id проверяет принадлежность комментария к объявлению.
     * @param adId идентификатор объявления
     * @param commentId идентификатор комментария
     * @return {@link true} - комментарий соответствует объявлению, {@link false} - не соответствует
     */
    @Override
    public boolean dataIsConsistent(int adId, int commentId) {
        return adId == commentRepository.findById(commentId).getAds().getId();
    }
}
