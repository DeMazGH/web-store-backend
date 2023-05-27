package ru.skypro.homework.mapper;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import ru.skypro.homework.dto.ResponseWrapperCommentDto;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static ru.skypro.homework.ConstantsTest.COMMENT_DTO_LIST;
import static ru.skypro.homework.ConstantsTest.COMMENT_LIST;

@SpringBootTest
class ResponseWrapperCommentDtoMapperTest {

    @SpyBean
    private ResponseWrapperCommentDtoMapper responseWrapperCommentDtoMapper;

    @Test
    void toResponseWrapperCommentDto() {
        ResponseWrapperCommentDto actual = responseWrapperCommentDtoMapper.toResponseWrapperCommentDto(COMMENT_LIST);

        assertThat(actual).isNotNull();
        assertThat(actual.getCount()).isEqualTo(COMMENT_LIST.size());
        assertThat(actual.getResults()).isEqualTo(COMMENT_DTO_LIST);
    }
}