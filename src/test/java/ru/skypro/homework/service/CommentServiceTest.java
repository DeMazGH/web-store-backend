package ru.skypro.homework.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import ru.skypro.homework.dto.CommentDto;
import ru.skypro.homework.entity.Comment;
import ru.skypro.homework.mapper.CommentMapper;
import ru.skypro.homework.repository.AdsRepository;
import ru.skypro.homework.repository.CommentRepository;
import ru.skypro.homework.repository.UserRepository;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static ru.skypro.homework.ConstantsTest.*;


//Тесты для класса в разработке, здесь только наброски для экспериментов, прошу игнорировать этот класс при проверке
@ExtendWith(MockitoExtension.class)
class CommentServiceTest {

    @Spy
    CommentRepository commentRepository;
    @Spy
    AdsRepository adsRepository;
    @Spy
    UserRepository userRepository;

    @Spy
    CommentMapper commentMapper;

    @InjectMocks
    CommentService out;

    @Mock
    private Authentication auth;

//    @BeforeEach
//    public void initSecurityContext() {
//        when(auth.getName()).thenReturn(EMAIL);
//        SecurityContextHolder.getContext().setAuthentication(auth);
//    }
//
//    @AfterEach
//    public void clearSecurityContext() {
//        SecurityContextHolder.clearContext();
//    }

    @Test
    void getAdsComments() {
    }

    @Test
    void addCommentToAd() {
//        when(commentRepository.save(any(Comment.class))).thenReturn(COMMENT_TEST_1);
//        Здесь же нужно замокать и все методы маппера и репозитория, что тогда вообще будет проверять этот тест?
    }
}