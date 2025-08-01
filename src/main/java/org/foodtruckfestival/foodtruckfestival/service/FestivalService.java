package org.foodtruckfestival.foodtruckfestival.service;

import org.foodtruckfestival.foodtruckfestival.domain.Festival;
import org.foodtruckfestival.foodtruckfestival.dto.FestivalDTO;
import org.foodtruckfestival.foodtruckfestival.enums.Food;

import java.time.LocalDateTime;
import java.util.List;

public interface FestivalService {
Festival findById(Long id);
List<Festival> findAll();
Festival save(Festival festival);
void deleteById(Long id);
List<FestivalDTO> fetchFestivalOverview();
int getAvailableTickets(Long festivalId);
public FestivalDTO findFestivalDTOById(Long id);
}
