package org.foodtruckfestival.foodtruckfestival.service;

import org.foodtruckfestival.foodtruckfestival.domain.Festival;
import org.foodtruckfestival.foodtruckfestival.dto.FestivalDTO;
import lombok.extern.slf4j.Slf4j;
import org.foodtruckfestival.foodtruckfestival.enums.Food;
import org.foodtruckfestival.foodtruckfestival.exceptions.CategoryNotFoundException;
import org.foodtruckfestival.foodtruckfestival.exceptions.FestivalNotFoundException;
import org.foodtruckfestival.foodtruckfestival.exceptions.NoFestivalsException;
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
        return festivalRepository.findById(id).orElseThrow(()->new FestivalNotFoundException(id.intValue()));
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
            List<Festival> festivals = festivalRepository.findAll();
            if (festivals.isEmpty()) {
                throw new NoFestivalsException("No Festivals found");
            }

            return festivals.stream()
                    .sorted(Comparator.comparing(Festival::getDateTime))
                    .map(festival -> {
                        int registrations = festival.getRegistrations().stream().mapToInt(r -> r.amountOfTickets).sum();
                        int availableTickets = festivalRepository.findAvailableTicketsByFestivalId(festival.getId());
                        boolean isFuture = festival.getDateTime().isAfter(LocalDateTime.now());

                        return new FestivalDTO(
                                festival.getId(),
                                festival.getName(),
                                festival.getLocation().name(),
                                festival.getCategorie().toString(),
                                festival.getDateTime(),
                                availableTickets,
                                festival.getPrice() ,
                                registrations,
                                festival.getFoodtrucks()
                        );
                    })
                    .collect(Collectors.toList());
        }

        @Override
        public int getAvailableTickets(Long festivalId) {
            festivalRepository.findById(festivalId)
                    .orElseThrow(() -> new FestivalNotFoundException(festivalId.intValue()));

            return festivalRepository.findAvailableTicketsByFestivalId(festivalId);
        }

        @Override
        public FestivalDTO findFestivalDTOById(Long id) {
            Festival festival = festivalRepository.findById(id).orElseThrow(()->new FestivalNotFoundException(id.intValue()));

            int registrationsCount = festival.getRegistrations().stream().mapToInt(r -> r.amountOfTickets).sum();
            int availableTickets = festivalRepository.findAvailableTicketsByFestivalId(festival.getId());

            return new FestivalDTO(
                    festival.getId(),
                    festival.getName(),
                    festival.getLocation().name(),
                    festival.getCategorie().toString(),
                    festival.getDateTime(),
                    availableTickets,
                    festival.getPrice(),
                    registrationsCount,
                    festival.getFoodtrucks()
            );
        }

        @Override
        public Festival updateFestival(Long id, Festival updatedFestival) {
            Festival existingFestival = festivalRepository.findById(id)
                    .orElseThrow(() -> new FestivalNotFoundException(id.intValue()));

            existingFestival.setName(updatedFestival.getName());
            existingFestival.setFoodtrucks(updatedFestival.getFoodtrucks());
            existingFestival.setLocation(updatedFestival.getLocation());
            existingFestival.setCategorie(updatedFestival.getCategorie());
            existingFestival.setDateTime(updatedFestival.getDateTime());
            existingFestival.setFestivalCode1(updatedFestival.getFestivalCode1());
            existingFestival.setFestivalCode2(updatedFestival.getFestivalCode2());
            existingFestival.setMaxTickets(updatedFestival.getMaxTickets());
            existingFestival.setPrice(updatedFestival.getPrice());

            return festivalRepository.save(existingFestival);
        }

        @Override
        public List<Festival> getFestivalsByCategory(Food category) {
            List<Festival> festivals = festivalRepository.findByCategorie(category);

            if (festivals.isEmpty()) {
                throw new CategoryNotFoundException("No festivals found for category: " + category);
            }

            return festivals;
        }
    }
