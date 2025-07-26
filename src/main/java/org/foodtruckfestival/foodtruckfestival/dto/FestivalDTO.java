package org.foodtruckfestival.foodtruckfestival.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class FestivalDTO {
private Long id;
private String name;
private String location;
private String categorie;
private LocalDateTime dateTime;
private int availableTickets;
private BigDecimal price;
}
