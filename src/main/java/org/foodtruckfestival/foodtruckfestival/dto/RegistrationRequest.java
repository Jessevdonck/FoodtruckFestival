package org.foodtruckfestival.foodtruckfestival.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.foodtruckfestival.foodtruckfestival.validator.ValidAvailableTickets;

@Data
@ValidAvailableTickets
@Getter
@Setter
public class RegistrationRequest {

    @NotNull
    private Long festivalId;

    @Min(value = 1, message = "Je moet minstens 1 ticket bestellen.")
    private int ticketsToBuy;
}
