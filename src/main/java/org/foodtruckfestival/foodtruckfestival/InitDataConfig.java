package org.foodtruckfestival.foodtruckfestival;

import org.foodtruckfestival.foodtruckfestival.domain.*;
import org.foodtruckfestival.foodtruckfestival.enums.Food;
import org.foodtruckfestival.foodtruckfestival.enums.Location;
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
                .location(Location.KORTRIJK)
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
                    .location(Location.GENT)
                    .categorie(Food.MEXICAANS)
                    .dateTime(LocalDateTime.of(2025, 6, 3, 14, 0))
                    .festivalCode1(120)
                    .festivalCode2(210)
                    .maxTickets(150)
                    .price(new BigDecimal("20.00"))
                    .foodtrucks(Arrays.asList("Truck A", "Truck B", "Truck C"))
                    .build();
                Festival festival3 = Festival.builder()
                        .name("Gulzig Festival")
                        .location(Location.ANTWERPEN)
                        .categorie(Food.MEXICAANS)
                        .dateTime(LocalDateTime.of(2025, 6, 18, 19, 0))
                        .festivalCode1(120)
                        .festivalCode2(210)
                        .maxTickets(60)
                        .price(new BigDecimal("39.99"))
                        .foodtrucks(Arrays.asList("Truck A", "Truck B", "Truck C"))
                        .build();

        festivalRepository.saveAll(Arrays.asList(festival, festival2, festival3));

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

        Registration registration3 = Registration.builder()
                .user(admin)
                .festival(festival3)
                .amountOfTickets(1)
                .registrationTime(LocalDateTime.now())
                .build();

        registrationRepository.saveAll(Arrays.asList(registration, registration2, registration3));

        // REVIEW
        Review review = Review.builder()
                .user(admin)
                .festival(festival2)
                .score(4)
                .description("Great festival with delicious food trucks!")
                .postedAt(LocalDateTime.now())
                .build();

        reviewRepository.save(review);
    }
}
