package ru.skypro.homework.mapper;

import org.junit.jupiter.api.Test;
import ru.skypro.homework.dto.CommentDto;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static ru.skypro.homework.ConstantsTest.*;

class CommentMapperTest {

    @Test
    void shouldMapCommentToCommentDto() {
        CommentDto actual = CommentMapper.INSTANCE.commentToCommentDto(COMMENT_TEST);

        assertThat(actual).isNotNull();
        assertThat(actual.getAuthor()).isEqualTo(USER_ID);
        assertThat(actual.getCreatedAt()).isEqualTo(CREATED_AT);
        assertThat(actual.getPk()).isEqualTo(COMMENT_ID);
        assertThat(actual.getText()).isEqualTo(TEXT);
    }
}