package org.foodtruckfestival.foodtruckfestival;

import org.foodtruckfestival.foodtruckfestival.domain.Festival;
import org.foodtruckfestival.foodtruckfestival.enums.Food;
import org.foodtruckfestival.foodtruckfestival.exceptions.FestivalNotFoundException;
import org.foodtruckfestival.foodtruckfestival.service.FestivalService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class FestivalRestTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private FestivalService festivalService;

    @Test
    void testGetFestivalsByCategory() throws Exception {
        Festival festival1 = new Festival();
        festival1.setId(1L);
        festival1.setName("Vegan Vibes");
        festival1.setCategorie(Food.VEGAN);
        festival1.setDateTime(LocalDateTime.of(2025, 8, 10, 14, 0));
        festival1.setPrice(new BigDecimal("25.00"));

        Festival festival2 = new Festival();
        festival2.setId(2L);
        festival2.setName("Green Bites");
        festival2.setCategorie(Food.VEGAN);
        festival2.setDateTime(LocalDateTime.of(2025, 9, 1, 12, 0));
        festival2.setPrice(new BigDecimal("30.00"));

        when(festivalService.getFestivalsByCategory(Food.VEGAN)).thenReturn(List.of(festival1, festival2));

        mockMvc.perform(get("/api/festivals").param("category", "VEGAN"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Vegan Vibes"))
                .andExpect(jsonPath("$[1].name").value("Green Bites"));
    }

    @Test
    void testGetFestivalsByCategory_MissingParameter() throws Exception {
        mockMvc.perform(get("/api/festivals"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testGetFestivalsByCategory_InvalidParameter() throws Exception {
        mockMvc.perform(get("/api/festivals").param("category", "UNKNOWN"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testGetFestivalsByCategory_NotFound() throws Exception {
        Food unknownCategory = Food.VEGAN;

        when(festivalService.getFestivalsByCategory(unknownCategory))
                .thenThrow(new FestivalNotFoundException("No festivals found for category: " + unknownCategory));

        mockMvc.perform(get("/api/festivals").param("category", unknownCategory.name()))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("No festivals found for category: " + unknownCategory));
    }

    @Test
    void testGetAvailableTickets() throws Exception {
        Long festivalId = 1L;
        when(festivalService.getAvailableTickets(festivalId)).thenReturn(42);

        mockMvc.perform(get("/api/festival/{id}/tickets", festivalId))
                .andExpect(status().isOk())
                .andExpect(content().string("42"));
    }

    @Test
    void testGetAvailableTickets_FestivalNotFound() throws Exception {
        Long nonExistingId = 999L;

        when(festivalService.getAvailableTickets(nonExistingId))
                .thenThrow(new RuntimeException("Festival not found"));

        mockMvc.perform(get("/api/festival/{id}/tickets", nonExistingId))
                .andExpect(status().isInternalServerError());
    }

    @Test
    void testGetAvailableTickets_InvalidId() throws Exception {
        mockMvc.perform(get("/api/festival/abc/tickets"))
                .andExpect(status().isBadRequest());
    }
}
