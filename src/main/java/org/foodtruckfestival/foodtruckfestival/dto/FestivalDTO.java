package org.foodtruckfestival.foodtruckfestival.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
public class FestivalDTO {
private Long id;
private String name;
private String location;
private String categorie;
@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
private LocalDateTime dateTime;
private int availableTickets;
private BigDecimal price;
private int registrationCount;
private List<String> foodtrucks;
}
