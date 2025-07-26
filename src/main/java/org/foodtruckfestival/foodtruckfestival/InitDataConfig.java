package org.foodtruckfestival.foodtruckfestival;

import org.foodtruckfestival.foodtruckfestival.domain.*;
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
        .dateTime(LocalDateTime.of(2025, 8, 20, 14, 0))
        .festivalCode1(101)
        .festivalCode2(202)
        .maxTickets(5000)
        .price(new BigDecimal("59.99"))
        .foodtrucks(Arrays.asList("Truck A", "Truck B", "Truck C"))
        .build();

festivalRepository.save(festival);

// REGISTRATION
Registration registration = Registration.builder()
        .user(user)
        .festival(festival)
        .aantalTickets(2)
        .registrationTime(LocalDateTime.now())
        .build();

registrationRepository.save(registration);

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
