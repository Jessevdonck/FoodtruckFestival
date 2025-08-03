package org.foodtruckfestival.foodtruckfestival.service;

import org.foodtruckfestival.foodtruckfestival.domain.Festival;
import org.foodtruckfestival.foodtruckfestival.domain.MyUser;
import org.foodtruckfestival.foodtruckfestival.domain.Registration;
import org.foodtruckfestival.foodtruckfestival.exceptions.RegistrationNotFoundException;
import org.foodtruckfestival.foodtruckfestival.repository.FestivalRepository;
import org.foodtruckfestival.foodtruckfestival.repository.MyUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.foodtruckfestival.foodtruckfestival.repository.RegistrationRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RegistrationServiceImpl implements RegistrationService
    {

        @Autowired
        RegistrationRepository registrationRepository;
        @Autowired
        FestivalRepository festivalRepository;
        @Autowired
        MyUserRepository myUserRepository;

        @Override
        public Registration findById(Long id) {
            return registrationRepository.findById(id).orElseThrow(()->new RegistrationNotFoundException(id));
        }

        @Override
        public List<Registration> findAll() {
        return registrationRepository.findAll();
        }

        @Override
        public Registration save(Registration registration) {
        return registrationRepository.save(registration);
        }

        @Override
        public void deleteById(Long id) {
        registrationRepository.deleteById(id);
        }

        public String registerForFestival(Long festivalId, String username, int ticketsToBuy) {

            Festival festival = festivalRepository.findById(festivalId)
                    .orElseThrow(() -> new RuntimeException("Festival niet gevonden"));

            MyUser user = myUserRepository.findByUsername(username);

            Registration registration = Registration.builder()
                    .festival(festival)
                    .user(user)
                    .amountOfTickets(ticketsToBuy)
                    .registrationTime(LocalDateTime.now())
                    .build();

            registrationRepository.save(registration);

            return ticketsToBuy + " tickets aangekocht.";
        }
}
