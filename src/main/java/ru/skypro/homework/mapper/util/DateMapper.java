package ru.skypro.homework.mapper.util;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.TimeZone;

public class DateMapper {

    public long localDateTimeToLong(LocalDateTime localDateTime) {
        return localDateTime.toInstant(ZoneOffset.UTC).toEpochMilli();
    }

    public LocalDateTime longToLocalDateTime(long longNum) {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(longNum), TimeZone.getDefault().toZoneId());
    }
}
