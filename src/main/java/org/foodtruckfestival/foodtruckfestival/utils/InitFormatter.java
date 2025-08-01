package org.foodtruckfestival.foodtruckfestival.utils;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public interface InitFormatter {

    DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

    DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#.00", DecimalFormatSymbols.getInstance(Locale.getDefault()));
}
