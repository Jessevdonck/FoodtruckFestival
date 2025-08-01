package org.foodtruckfestival.foodtruckfestival.service;

import org.foodtruckfestival.foodtruckfestival.domain.Festival;
import org.foodtruckfestival.foodtruckfestival.domain.Registration;
import org.foodtruckfestival.foodtruckfestival.dto.FestivalDTO;
import lombok.extern.slf4j.Slf4j;
import org.foodtruckfestival.foodtruckfestival.exceptions.FestivalConflictException;
import org.foodtruckfestival.foodtruckfestival.exceptions.FestivalNotFoundException;
import org.foodtruckfestival.foodtruckfestival.exceptions.NoFestivalsException;
import org.foodtruckfestival.foodtruckfestival.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.foodtruckfestival.foodtruckfestival.repository.FestivalRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class FestivalServiceImpl implements FestivalService
    {
    private static final LocalDate FESTIVAL_START_DATE = LocalDate.of(2025, 7, 1);
    private static final LocalDate FESTIVAL_END_DATE = LocalDate.of(2025, 7, 31);

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
            // Check dubbel naam in periode
            boolean existsSameNameInPeriod = festivalRepository.existsByNameAndDateTimeBetween(
                    festival.getName(),
                    FESTIVAL_START_DATE.atStartOfDay(),
                    FESTIVAL_END_DATE.atTime(23, 59, 59)
            );

            if (existsSameNameInPeriod) {
                throw new FestivalConflictException("Er bestaat al een festival met dezelfde naam binnen deze periode.");
            }

            // Check zelfde categorie op dezelfde dag
            boolean existsSameCategoryOnSameDay = festivalRepository.existsByCategorieAndDateTimeBetween(
                    festival.getCategorie(),
                    festival.getDateTime().withHour(0).withMinute(0).withSecond(0).withNano(0),
                    festival.getDateTime().withHour(23).withMinute(59).withSecond(59).withNano(999999999)
            );

            if (existsSameCategoryOnSameDay) {
                throw new FestivalConflictException("Er is al een festival met dezelfde categorie op dezelfde dag.");
            }

            // Als alles ok, sla op
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
                    int registrations = festival.getRegistrations().stream().mapToInt(r -> r.aantalTickets).sum();
                    int availableTickets = festivalRepository.findAvailableTicketsByFestivalId(festival.getId());
                    boolean isFuture = festival.getDateTime().isAfter(LocalDateTime.now());

                    return new FestivalDTO(
                            festival.getId(),
                            festival.getName(),
                            festival.getLocation(),
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

    public int getAvailableTickets(Long festivalId)
        {
            int available = festivalRepository.findAvailableTicketsByFestivalId(festivalId);

            return available;
        }

    @Override
    public FestivalDTO findFestivalDTOById(Long id) {
    Festival festival = festivalRepository.findById(id).orElseThrow(()->new FestivalNotFoundException(id.intValue()));

    int registrationsCount = festival.getRegistrations().stream().mapToInt(r -> r.aantalTickets).sum();
    int availableTickets = festivalRepository.findAvailableTicketsByFestivalId(festival.getId());

    return new FestivalDTO(
            festival.getId(),
            festival.getName(),
            festival.getLocation(),
            festival.getCategorie().toString(),
            festival.getDateTime(),
            availableTickets,
            festival.getPrice(),
            registrationsCount,
            festival.getFoodtrucks()
    );
    }
    }
