package org.foodtruckfestival.foodtruckfestival.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.foodtruckfestival.foodtruckfestival.utils.InitFormatter.DATE_FORMATTER;

public class FestivalConstants {
    public static final LocalDate START_DATE = LocalDate.parse("01-06-2025", DATE_FORMATTER);
    public static final LocalDate END_DATE = LocalDate.parse("31-10-2025", DATE_FORMATTER);

}
