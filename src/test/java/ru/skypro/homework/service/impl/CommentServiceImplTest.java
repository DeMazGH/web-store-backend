package ru.skypro.homework.service.impl;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import ru.skypro.homework.config.WithMockCustomUser;
import ru.skypro.homework.dto.CommentDto;
import ru.skypro.homework.dto.ResponseWrapperCommentDto;
import ru.skypro.homework.entity.Comment;
import ru.skypro.homework.exception.AdNotFoundException;
import ru.skypro.homework.exception.CommentNotFoundException;
import ru.skypro.homework.mapper.CommentMapper;
import ru.skypro.homework.mapper.ResponseWrapperCommentDtoMapper;
import ru.skypro.homework.repository.AdsRepository;
import ru.skypro.homework.repository.CommentRepository;
import ru.skypro.homework.repository.UserRepository;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static ru.skypro.homework.ConstantsTest.*;

@SpringBootTest
class CommentServiceImplTest {

    @InjectMocks
    private CommentServiceImpl out;

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private AdsRepository adsRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private CommentMapper commentMapper;

    @Mock
    private ResponseWrapperCommentDtoMapper responseWrapperCommentDtoMapper;

    @Test
    void shouldReturnCorrectResponseWrapperCommentDtoInMethodGetAdComments() {
        when(commentRepository.findByAds_Id(ADS_ID_1)).thenReturn(COMMENT_LIST);
        when(responseWrapperCommentDtoMapper.toResponseWrapperCommentDto(COMMENT_LIST))
                .thenReturn(RESPONSE_WRAPPER_COMMENT_DTO);

        ResponseWrapperCommentDto actual = out.getAdComments(ADS_ID_1);

        assertEquals(RESPONSE_WRAPPER_COMMENT_DTO, actual);
        verify(commentRepository, times(1)).findByAds_Id(ADS_ID_1);
        verify(responseWrapperCommentDtoMapper, times(1)).toResponseWrapperCommentDto(COMMENT_LIST);
    }

    @Test
    @WithMockCustomUser(name = EMAIL)
    void shouldReturnCorrectCommentDtoInMethodAddCommentToAd() {
        Comment testNewComment = new Comment();
        testNewComment.setText(TEXT_1);
        testNewComment.setCreatedAt(CREATED_AT_LOCAL_DATE_TIME);
        testNewComment.setAds(ADS_TEST_1);
        testNewComment.setUser(USER_TEST);

        Comment createdComment = new Comment();
        createdComment.setText(TEXT_1);

        when(adsRepository.findById((int) ADS_ID_1)).thenReturn(ADS_TEST_1);
        when(userRepository.findByEmail(EMAIL)).thenReturn(USER_TEST);
        when(commentRepository.save(testNewComment)).thenReturn(COMMENT_TEST_1);
        when(commentMapper.createCommentDtoToComment(CREATE_COMMENT_DTO_TEST)).thenReturn(createdComment);
        when(commentMapper.commentToCommentDto(COMMENT_TEST_1)).thenReturn(COMMENT_DTO_TEST_1);

        try (MockedStatic<LocalDateTime> theMock = Mockito.mockStatic(LocalDateTime.class)) {
            theMock.when(LocalDateTime::now).thenReturn(CREATED_AT_LOCAL_DATE_TIME);

            CommentDto actual = out.addCommentToAd(ADS_ID_1, CREATE_COMMENT_DTO_TEST);

            assertEquals(COMMENT_DTO_TEST_1, actual);
            verify(adsRepository, only()).findById((int) ADS_ID_1);
            verify(userRepository, only()).findByEmail(EMAIL);
            verify(commentRepository, only()).save(testNewComment);
            verify(commentMapper, times(1)).createCommentDtoToComment(CREATE_COMMENT_DTO_TEST);
            verify(commentMapper, times(1)).commentToCommentDto(COMMENT_TEST_1);
        }
    }

    @Test
    void shouldThrowAdNotFoundExceptionInMethodAddCommentToAd() {
        when(adsRepository.findById((int) ADS_ID_1)).thenReturn(null);

        assertThrows(AdNotFoundException.class, () -> out.addCommentToAd(ADS_ID_1, CREATE_COMMENT_DTO_TEST),
                "Ad doesn't exist");
        verify(adsRepository, only()).findById((int) ADS_ID_1);
    }

    @Test
    void shouldInvokeDeleteInMethodDeleteAdsComment() {
        out.deleteAdsComment(COMMENT_ID_1);
        verify(commentRepository, only()).deleteById(COMMENT_ID_1);
    }

    @Test
    void shouldReturnCorrectCommentDtoInMethodUpdateAdComment() {
        CommentDto newCommentData = COMMENT_DTO_TEST_1;
        newCommentData.setText(TEXT_2);

        Comment updatedComment = COMMENT_TEST_1;
        updatedComment.setText(TEXT_2);

        when(commentRepository.findById((int) COMMENT_ID_1)).thenReturn(COMMENT_TEST_1);
        when(commentRepository.save(updatedComment)).thenReturn(updatedComment);
        when(commentMapper.commentToCommentDto(updatedComment)).thenReturn(newCommentData);

        CommentDto actual = out.updateAdComment(COMMENT_ID_1, newCommentData);

        assertEquals(newCommentData, actual);
        verify(commentRepository, times(1)).findById((int) COMMENT_ID_1);
        verify(commentRepository, times(1)).save(updatedComment);
        verify(commentMapper, only()).commentToCommentDto(updatedComment);
    }

    @Test
    void shouldThrowCommentNotFoundExceptionInMethodUpdateAdComment() {
        when(commentRepository.findById((int) COMMENT_ID_1)).thenReturn(null);

        assertThrows(CommentNotFoundException.class, () -> out.updateAdComment(ADS_ID_1, COMMENT_DTO_TEST_1),
                "Comment doesn't exist");
        verify(commentRepository, only()).findById((int) COMMENT_ID_1);
    }

    @Test
    void shouldReturnTrueInMethodDataIsConsistent() {
        when(commentRepository.findById((int) COMMENT_ID_1)).thenReturn(COMMENT_TEST_1);
        when(adsRepository.findById((int) ADS_ID_1)).thenReturn(ADS_TEST_1);

        assertTrue(out.dataIsConsistent(ADS_ID_1, COMMENT_ID_1));
        verify(commentRepository, only()).findById((int) COMMENT_ID_1);
        verify(adsRepository, only()).findById((int) ADS_ID_1);
    }

    @Test
    void shouldReturnFalseInMethodDataIsConsistent() {
        when(adsRepository.findById((int) ADS_ID_1)).thenReturn(ADS_TEST_1);

        when(commentRepository.findById((int) COMMENT_ID_1)).thenReturn(null);

        assertFalse(out.dataIsConsistent(ADS_ID_1, COMMENT_ID_1));
        verify(adsRepository, only()).findById((int) ADS_ID_1);
        verify(commentRepository, only()).findById((int) COMMENT_ID_1);
    }

}