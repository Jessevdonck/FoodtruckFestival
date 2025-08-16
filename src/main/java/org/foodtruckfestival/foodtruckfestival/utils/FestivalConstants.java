package org.foodtruckfestival.foodtruckfestival.utils;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.foodtruckfestival.foodtruckfestival.utils.InitFormatter.DATE_FORMATTER;

public class FestivalConstants {
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    public static final LocalDate START_DATE = LocalDate.parse("01-06-2025", DATE_FORMATTER);
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    public static final LocalDate END_DATE = LocalDate.parse("31-10-2025", DATE_FORMATTER);

}
