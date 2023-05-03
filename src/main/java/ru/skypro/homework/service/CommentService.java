package ru.skypro.homework.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.ResponseWrapperCommentDto;
import ru.skypro.homework.mapper.ResponseWrapperCommentDtoMapper;
import ru.skypro.homework.repository.CommentRepository;

@Slf4j
@Service
public class CommentService {

    private final CommentRepository commentRepository;

    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public ResponseWrapperCommentDto getAdsComments(int adId) {
        return ResponseWrapperCommentDtoMapper.INSTANCE.toResponseWrapperCommentDto(commentRepository.findByAds_Pk(adId));
    }


}
