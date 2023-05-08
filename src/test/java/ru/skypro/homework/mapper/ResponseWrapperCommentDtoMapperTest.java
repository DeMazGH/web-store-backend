package ru.skypro.homework.mapper;

import org.junit.jupiter.api.Test;
import ru.skypro.homework.dto.ResponseWrapperCommentDto;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static ru.skypro.homework.ConstantsTest.COMMENT_DTO_LIST;
import static ru.skypro.homework.ConstantsTest.COMMENT_LIST;

class ResponseWrapperCommentDtoMapperTest {

    @Test
    void toResponseWrapperCommentDto() {
        ResponseWrapperCommentDto actual = ResponseWrapperCommentDtoMapper.INSTANCE.toResponseWrapperCommentDto(COMMENT_LIST);

        assertThat(actual).isNotNull();
        assertThat(actual.getCount()).isEqualTo(COMMENT_LIST.size());
        assertThat(actual.getResults()).isEqualTo(COMMENT_DTO_LIST);
    }
}