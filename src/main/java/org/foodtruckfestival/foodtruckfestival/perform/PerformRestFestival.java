package org.foodtruckfestival.foodtruckfestival.perform;

import jakarta.annotation.PostConstruct;
import org.foodtruckfestival.foodtruckfestival.domain.Festival;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import java.util.List;

@Component
public class PerformRestFestival {

    private final String SERVER_URI = "http://localhost:8080/api";
    private final WebClient webClient = WebClient.create();

    @PostConstruct
    public void init() {
        System.out.println("---- ---- GET FESTIVALS BY CATEGORY ---- ----");
        try {
            getFestivalsByCategory("AZE"); // foute parameter
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        System.out.println("---- ---- ---- ---- ---- ---- ---- ----");

        System.out.println("---- ---- GET AVAILABLE TICKETS ---- ----");
        try {
            getAvailableTickets(1L);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        System.out.println("---- ---- ---- ---- ---- ---- ---- ----");
    }

    private void getFestivalsByCategory(String category) {
        try {
            List<Festival> festivals = webClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .scheme("http")
                            .host("localhost")
                            .port(8080)
                            .path("/api/festivals")
                            .queryParam("category", category)
                            .build())
                    .retrieve()
                    .bodyToFlux(Festival.class)
                    .collectList()
                    .block();

            if (festivals != null && !festivals.isEmpty()) {
                festivals.forEach(f -> System.out.println("Found festival: " + f.getName()));
            } else {
                System.out.println("No festivals found for category: " + category);
            }

        } catch (WebClientResponseException.NotFound e) {
            System.out.println("Festival not found for category: " + category);
        } catch (Exception e) {
            System.out.println("Error fetching festivals: " + e.getMessage());
        }
    }

    private void getAvailableTickets(Long festivalId) {
        try {
            String response = webClient.get()
                    .uri(SERVER_URI + "/festival/" + festivalId + "/tickets")
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            System.out.println("Aantal beschikbare tickets: " + response);

        } catch (WebClientResponseException.NotFound e) {
            System.out.println("Festival not found with ID: " + festivalId);
        } catch (Exception e) {
            System.out.println("Error fetching tickets: " + e.getMessage());
        }
    }
}
