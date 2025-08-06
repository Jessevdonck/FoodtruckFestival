package org.foodtruckfestival.foodtruckfestival.utils;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public interface InitFormatter {

    DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm:ss");
    DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("MM-dd-yyyy");
    DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#.00", DecimalFormatSymbols.getInstance(Locale.getDefault()));
}