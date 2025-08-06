package org.foodtruckfestival.foodtruckfestival;

import org.foodtruckfestival.foodtruckfestival.domain.*;
import org.foodtruckfestival.foodtruckfestival.enums.Food;
import org.foodtruckfestival.foodtruckfestival.enums.Role;
import org.foodtruckfestival.foodtruckfestival.repository.FestivalRepository;
import org.foodtruckfestival.foodtruckfestival.repository.MyUserRepository;
import org.foodtruckfestival.foodtruckfestival.repository.RegistrationRepository;
import org.foodtruckfestival.foodtruckfestival.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;

@Component
public class InitDataConfig implements CommandLineRunner {

        private final PasswordEncoder encoder = new BCryptPasswordEncoder();

        @Autowired
        private MyUserRepository myUserRepository;
        @Autowired
        private FestivalRepository festivalRepository;
        @Autowired
        private RegistrationRepository registrationRepository;
        @Autowired
        private ReviewRepository reviewRepository;

        @Override
        public void run(String... args) throws Exception {
        // USERS
        MyUser user = MyUser.builder()
                .username("john doe")
                .password(encoder.encode("userpass"))
                .role(Role.USER)
                .build();

        MyUser admin = MyUser.builder()
                .username("admin")
                .password(encoder.encode("adminpass"))
                .role(Role.ADMIN)
                .build();

        myUserRepository.saveAll(Arrays.asList(user, admin));

        // FESTIVAL
        Festival festival = Festival.builder()
                .name("Summer Fest")
                .location("Amsterdam")
                .categorie(Food.BBQ)
                .dateTime(LocalDateTime.of(2025, 10, 3, 14, 0))
                .festivalCode1(120)
                .festivalCode2(210)
                .maxTickets(150)
                .price(new BigDecimal("12.50"))
                .foodtrucks(Arrays.asList("Truck A", "Truck B", "Truck C"))
                .build();

            Festival festival2 = Festival.builder()
                    .name("Smul Festival")
                    .location("Brussel")
                    .categorie(Food.MEXICAANS)
                    .dateTime(LocalDateTime.of(2025, 6, 3, 14, 0))
                    .festivalCode1(120)
                    .festivalCode2(210)
                    .maxTickets(150)
                    .price(new BigDecimal("20.00"))
                    .foodtrucks(Arrays.asList("Truck A", "Truck B", "Truck C"))
                    .build();

        festivalRepository.save(festival);
        festivalRepository.save(festival2);

        // REGISTRATION
        Registration registration = Registration.builder()
                .user(user)
                .festival(festival)
                .amountOfTickets(2)
                .registrationTime(LocalDateTime.now())
                .build();

        Registration registration2 = Registration.builder()
                .user(admin)
                .festival(festival2)
                .amountOfTickets(2)
                .registrationTime(LocalDateTime.now())
                .build();

            registrationRepository.save(registration);
            registrationRepository.save(registration2);

        // REVIEW
        Review review = Review.builder()
                .user(user)
                .festival(festival)
                .score(4)
                .description("Great festival with delicious food trucks!")
                .postedAt(LocalDateTime.now())
                .build();

        reviewRepository.save(review);
    }
}
