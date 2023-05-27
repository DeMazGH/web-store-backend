package ru.skypro.homework.mapper.util;

import org.mapstruct.Mapper;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.TimeZone;

@Mapper(componentModel = "spring")
public class DateMapper {

    public long localDateTimeToLong(LocalDateTime localDateTime) {
        return localDateTime.toInstant(ZoneOffset.systemDefault().getRules().getOffset(localDateTime)).toEpochMilli();
    }

    public LocalDateTime longToLocalDateTime(long longNum) {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(longNum), TimeZone.getDefault().toZoneId());
    }
}
