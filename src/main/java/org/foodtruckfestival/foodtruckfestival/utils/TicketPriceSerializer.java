package org.foodtruckfestival.foodtruckfestival.utils;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.math.BigDecimal;

import static org.foodtruckfestival.foodtruckfestival.utils.InitFormatter.DECIMAL_FORMAT;

public class TicketPriceSerializer extends JsonSerializer<BigDecimal> {

    @Override
    public void serialize(BigDecimal value, JsonGenerator gen, SerializerProvider serializers) throws IOException {

        String formattedValue = DECIMAL_FORMAT.format(value);
        gen.writeString(formattedValue);
    }
}