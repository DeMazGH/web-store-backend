package ru.skypro.homework.service.impl;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import ru.skypro.homework.config.WithMockCustomUser;
import ru.skypro.homework.dto.CommentDto;
import ru.skypro.homework.dto.ResponseWrapperCommentDto;
import ru.skypro.homework.entity.Comment;
import ru.skypro.homework.repository.AdsRepository;
import ru.skypro.homework.repository.CommentRepository;
import ru.skypro.homework.repository.UserRepository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static ru.skypro.homework.ConstantsTest.*;

//Тесты в работе
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


    @Test
    void shouldReturnCorrectResponseWrapperCommentDto() {
        when(commentRepository.findByAds_Id(ADS_ID_1)).thenReturn(COMMENT_LIST);

        ResponseWrapperCommentDto actual = out.getAdComments(ADS_ID_1);

        assertThat(actual).isNotNull();
        assertThat(actual.getCount()).isEqualTo(COMMENT_LIST.size());
        assertThat(actual.getResults()).isEqualTo(COMMENT_DTO_LIST);
    }

    @Test
    @WithMockCustomUser
    void shouldReturnCorrectCommentDto() {
        when(adsRepository.findById((int) ADS_ID_1)).thenReturn(ADS_TEST_1);
        when(userRepository.findByEmail(EMAIL)).thenReturn(USER_TEST);
        when(commentRepository.save(any(Comment.class))).thenReturn(COMMENT_TEST_1);

        CommentDto actual = out.addCommentToAd(ADS_ID_1, CREATE_COMMENT_DTO_TEST);

        assertThat(actual).isNotNull();
    }

    @Test
    void addCommentToAd() {
    }

    @Test
    void deleteAdsComment() {
    }

    @Test
    void updateAdComment() {
    }

    @Test
    void dataIsConsistent() {
    }
}