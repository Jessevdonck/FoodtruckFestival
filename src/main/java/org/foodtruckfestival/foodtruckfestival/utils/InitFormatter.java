package org.foodtruckfestival.foodtruckfestival.utils;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.ResourceBundle;

public interface InitFormatter {
    ResourceBundle bundle = ResourceBundle.getBundle("i18n.messages", Locale.getDefault());

    DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(bundle.getString("dateTime.format.pattern"));
    DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(bundle.getString("date.format.pattern"));
    DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#.00", DecimalFormatSymbols.getInstance(Locale.getDefault()));
}