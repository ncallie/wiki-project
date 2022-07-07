package com.ncallie.wiki.utils;


import com.fasterxml.jackson.databind.util.StdConverter;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class ConverterLocalDateTimeToLong extends StdConverter<LocalDateTime, String> {

    @Override
    public String convert(LocalDateTime localDateTime) {
        return String.valueOf(localDateTime.toEpochSecond(ZoneOffset.UTC));
    }
}
