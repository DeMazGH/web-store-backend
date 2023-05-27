package ru.skypro.homework.mapper.util;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;

import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static ru.skypro.homework.ConstantsTest.CREATED_AT_LOCAL_DATE_TIME;
import static ru.skypro.homework.ConstantsTest.CREATED_AT_LONG;

@SpringBootTest
class DateMapperTest {

    @SpyBean
    private DateMapper dateMapper;

    @Test
    void shouldMapLocalDateTimeToLong() {
        long actual = dateMapper.localDateTimeToLong(CREATED_AT_LOCAL_DATE_TIME);

        assertThat(actual).isNotNull();
        assertThat(actual).isEqualTo(CREATED_AT_LONG);
    }

    @Test
    void shouldMapLongToLocalDateTime() {
        LocalDateTime actual = dateMapper.longToLocalDateTime(CREATED_AT_LONG);

        assertThat(actual).isNotNull();
        assertThat(actual).isEqualTo(CREATED_AT_LOCAL_DATE_TIME);
    }
}