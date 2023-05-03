package ru.skypro.homework.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.skypro.homework.entity.Comment;
import ru.skypro.homework.repository.CommentRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static ru.skypro.homework.ConstantsTest.COMMENT_TEST_1;

@ExtendWith(MockitoExtension.class)
class CommentServiceTest {

    @Mock
    CommentRepository commentRepository;

    @InjectMocks
    CommentService commentService;

    @Test
    void getAdsComments() {
    }

    @Test
    void addCommentToAd() {
//        when(commentRepository.save(any(Comment.class))).thenReturn(COMMENT_TEST_1);
//        Здесь же нужно замокать и все методы маппера и репозитория, что тогда вообще будет проверять этот тест?
    }
}