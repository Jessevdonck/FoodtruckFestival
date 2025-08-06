package org.foodtruckfestival.foodtruckfestival;

import org.foodtruckfestival.foodtruckfestival.service.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("org.foodtruckfestival.foodtruckfestival.repository")
@EntityScan("org/foodtruckfestival/foodtruckfestival/domain")
public class FoodtruckFestivalApplication {

	public static void main(String[] args) {
		SpringApplication.run(FoodtruckFestivalApplication.class, args);
	}

	@Bean
	FestivalService festivalService() {
			return new FestivalServiceImpl();
		}

	@Bean
	RegistrationService registrationService() {
		return new RegistrationServiceImpl();
	}

	@Bean
	ReviewService reviewService() {
		return new ReviewServiceImpl();
	}
}
