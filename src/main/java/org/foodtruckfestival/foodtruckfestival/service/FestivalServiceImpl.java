package org.foodtruckfestival.foodtruckfestival.service;

import org.foodtruckfestival.foodtruckfestival.domain.Festival;
import org.foodtruckfestival.foodtruckfestival.dto.FestivalDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.foodtruckfestival.foodtruckfestival.repository.FestivalRepository;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class FestivalServiceImpl implements FestivalService
    {

@Autowired
private FestivalRepository festivalRepository;

@Override
public Festival findById(Long id) {
return festivalRepository.findById(id).orElse(null);
}

@Override
public List<Festival> findAll() {
return festivalRepository.findAll();
}

@Override
public Festival save(Festival festival) {
return festivalRepository.save(festival);
}

@Override
public void deleteById(Long id) {
festivalRepository.deleteById(id);
}

@Override
public List<FestivalDTO> fetchFestivalOverview() {
return festivalRepository.findAll().stream()
        .sorted(Comparator.comparing(Festival::getDateTime))
        .map(festival -> {
            int registrations = festival.getRegistrations() != null ? festival.getRegistrations().size() : 0;
            int availableTickets = festival.getMaxTickets() - registrations;
            boolean isFuture = festival.getDateTime().isAfter(LocalDateTime.now());
            return new FestivalDTO(
                    festival.getId(),
                    festival.getName(),
                    festival.getLocation(),
                    festival.getCategorie().toString(),
                    festival.getDateTime(),
                    availableTickets,
                    isFuture ? festival.getPrice() : null // Alleen tonen als nog niet gepasseerd
            );
        })
        .collect(Collectors.toList());
}

}
