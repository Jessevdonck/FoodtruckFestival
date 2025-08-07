package org.foodtruckfestival.foodtruckfestival.perform;

import org.foodtruckfestival.foodtruckfestival.domain.Festival;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

public class PerformRestFestival {

    // Base URL of the REST API
    private final String SERVER_URI = "http://localhost:8080/api";
    // WebClient instance
    private final WebClient webClient = WebClient.create();

    public PerformRestFestival() {
        System.out.println("---- ---- GET FESTIVALS BY CATEGORY ---- ----");
        try {
            getFestivalsByCategory("BBQ");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        System.out.println("---- ---- ---- ---- ---- ---- ---- ----");

        System.out.println("---- ---- GET AVAILABLE TICKETS ---- ----");
        try {
            getAvailableTickets(1L);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        System.out.println("---- ---- ---- ---- ---- ---- ---- ----");
    }

    public static void main(String[] args) {
        new PerformRestFestival();
    }

    private void getFestivalsByCategory(String category) {
        webClient.get()
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
                .doOnNext(this::printFestivalList)
                .block();
    }

    private void getAvailableTickets(Long festivalId) {
        webClient.get()
                .uri(SERVER_URI + "/festival/" + festivalId + "/tickets")
                .retrieve()
                .bodyToMono(String.class)
                .doOnNext(this::printTickets)
                .block();
    }

    private void printFestivalList(List<Festival> list) {
        list.forEach(festival -> System.out.println("Found festival: " + festival.getName()));
    }

    private void printTickets(String response) {
        System.out.println("Aantal beschikbare tickets: " + response);
    }
}
