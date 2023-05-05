package ru.skypro.homework.service;

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

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Slf4j
@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final AdsRepository adsRepository;
    private final UserRepository userRepository;

    public CommentService(CommentRepository commentRepository, AdsRepository adsRepository, UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.adsRepository = adsRepository;
        this.userRepository = userRepository;
    }

    public ResponseWrapperCommentDto getAdComments(int adId) {
        log.info("Was invoked method - getAdComments");
        return ResponseWrapperCommentDtoMapper.INSTANCE.toResponseWrapperCommentDto(commentRepository.findByAds_Id(adId));
    }

    public CommentDto addCommentToAd(int adId, CreateCommentDto createdComment) {
        log.info("Was invoked method - addCommentToAd");

        Comment newComment = CommentMapper.INSTANCE.createCommentDtoToComment(createdComment);
        newComment.setCreatedAt(LocalDateTime.now().toInstant(ZoneOffset.UTC).toEpochMilli());
        newComment.setAds(adsRepository.getReferenceById(adId));
        newComment.setUser(userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName()));

        Comment savedComment = commentRepository.save(newComment);

        return CommentMapper.INSTANCE.commentToCommentDto(savedComment);
    }

    public void deleteAdsComment(int commentId) {
        log.info("Was invoked method - deleteAdsComment");
        commentRepository.deleteById(commentId);
    }

    public CommentDto updateAdComment(int commentId, CommentDto newData) {
        log.info("Was invoked method - updateAdComment");

        Comment updatedComment = commentRepository.findById(commentId);
        if (updatedComment == null) {
            return null;
        }

        updatedComment.setText(newData.getText());
        commentRepository.save(updatedComment);
        return CommentMapper.INSTANCE.commentToCommentDto(updatedComment);
    }
}
