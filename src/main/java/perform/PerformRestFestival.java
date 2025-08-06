package perform;

import org.foodtruckfestival.foodtruckfestival.domain.Festival;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Map;

public class PerformRestFestival {

    // Base URL of the REST API
    private final String SERVER_URI = "http://localhost:8080/api";
    // WebClient instance
    private final WebClient webClient = WebClient.create();

    public PerformRestFestival() {
        System.out.println("---- ---- GET FESTIVALS BY CATEGORY ---- ----");
        getFestivalsByCategory("BBQ");
        System.out.println("---- ---- ---- ---- ---- ---- ---- ----");

        System.out.println("---- ---- GET AVAILABLE TICKETS ---- ----");
        getAvailableTickets(1L);
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
                .doOnError(error -> System.err.println("Error occurred: " + error.getMessage()))
                .block();
    }

    private void getAvailableTickets(Long festivalId) {
        webClient.get()
                .uri(SERVER_URI + "/festival/" + festivalId + "/tickets")
                .retrieve()
                .bodyToMono(Map.class)
                .doOnNext(this::printTickets)
                .doOnError(error -> System.err.println("Error occurred: " + error.getMessage()))
                .block();
    }

    private void printFestivalList(List<Festival> list) {
        list.forEach(festival -> System.out.println("Found festival: " + festival.getName()));
    }

    private void printTickets(Map<String, Integer> response) {
        System.out.println("Festival ID: " + response.get("festivalId"));
        System.out.println("Available Tickets: " + response.get("availableTickets"));
    }
}
