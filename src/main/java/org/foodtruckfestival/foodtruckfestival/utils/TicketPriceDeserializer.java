package org.foodtruckfestival.foodtruckfestival.utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.math.BigDecimal;

import static org.foodtruckfestival.foodtruckfestival.utils.InitFormatter.DECIMAL_FORMAT;

public class TicketPriceDeserializer extends JsonDeserializer<BigDecimal> {

    @Override
    public BigDecimal deserialize(JsonParser parser, DeserializationContext context) throws IOException {
        String value = parser.getValueAsString();
        if (value == null || value.isEmpty()) {
            return null;
        }
        try {
            Number parsedNumber = DECIMAL_FORMAT.parse(value);
            return new BigDecimal(parsedNumber.toString());
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid value for ticket price field: " + value, e);
        }
    }

}


