package org.foodtruckfestival.foodtruckfestival;

import org.foodtruckfestival.foodtruckfestival.service.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.foodtruckfestival.foodtruckfestival.perform.PerformRestFestival;

@SpringBootApplication
@EnableJpaRepositories("org.foodtruckfestival.foodtruckfestival.repository")
@EntityScan("org/foodtruckfestival/foodtruckfestival/domain")
public class FoodtruckFestivalApplication implements WebMvcConfigurer {

	public static void main(String[] args) {
		SpringApplication.run(FoodtruckFestivalApplication.class, args);
			new PerformRestFestival();
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
