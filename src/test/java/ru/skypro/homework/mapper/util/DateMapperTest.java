package ru.skypro.homework.mapper.util;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static ru.skypro.homework.ConstantsTest.CREATED_AT_LOCAL_DATE_TIME;
import static ru.skypro.homework.ConstantsTest.CREATED_AT_LONG;

class DateMapperTest {

    @Test
    void shouldMapLocalDateTimeToLong() {
        DateMapper dateMapper = new DateMapper();
        long actual = dateMapper.localDateTimeToLong(CREATED_AT_LOCAL_DATE_TIME);

        assertThat(actual).isNotNull();
        assertThat(actual).isEqualTo(CREATED_AT_LONG);
    }

    @Test
    void shouldMapLongToLocalDateTime() {
        DateMapper dateMapper = new DateMapper();
        LocalDateTime actual = dateMapper.longToLocalDateTime(CREATED_AT_LONG);

        assertThat(actual).isNotNull();
        assertThat(actual).isEqualTo(CREATED_AT_LOCAL_DATE_TIME);
    }
}