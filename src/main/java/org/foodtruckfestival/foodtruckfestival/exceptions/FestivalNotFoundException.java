package org.foodtruckfestival.foodtruckfestival.exceptions;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

public class FestivalNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public FestivalNotFoundException(int festivalId, MessageSource messageSource) {
        super(messageSource.getMessage(
                "festival.notfound.id",
                new Object[]{festivalId},
                LocaleContextHolder.getLocale()
        ));
    }

    public FestivalNotFoundException(String message) {
        super(message);
    }
}
