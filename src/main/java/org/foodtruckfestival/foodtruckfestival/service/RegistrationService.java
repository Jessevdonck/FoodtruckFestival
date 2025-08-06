package org.foodtruckfestival.foodtruckfestival.service;

import org.foodtruckfestival.foodtruckfestival.domain.Festival;
import org.foodtruckfestival.foodtruckfestival.domain.MyUser;
import org.foodtruckfestival.foodtruckfestival.domain.Registration;

import java.time.LocalDateTime;
import java.util.List;

public interface RegistrationService {
    Registration findById(Long id);
    List<Registration> findAll();
    Registration save(Registration registration);
    void deleteById(Long id);
    String registerForFestival(Long festivalId, String username, int ticketsToBuy);
    int countTicketsForUserInFestivalPeriod(String username, Long festivalId);
    int totalTicketsForFestivalByUser(MyUser user, Festival festival);
}
