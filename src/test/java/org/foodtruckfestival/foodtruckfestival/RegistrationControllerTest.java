package org.foodtruckfestival.foodtruckfestival;

import org.foodtruckfestival.foodtruckfestival.domain.Festival;
import org.foodtruckfestival.foodtruckfestival.dto.FestivalDTO;
import org.foodtruckfestival.foodtruckfestival.dto.RegistrationRequest;
import org.foodtruckfestival.foodtruckfestival.service.FestivalService;
import org.foodtruckfestival.foodtruckfestival.service.RegistrationService;
import org.foodtruckfestival.foodtruckfestival.validator.RegistrationValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.validation.BindingResult;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class RegistrationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private FestivalService festivalService;

    @MockitoBean
    private RegistrationService registrationService;

    @MockitoBean
    private RegistrationValidator registrationValidator;

    private FestivalDTO festivalDTO;

    @BeforeEach
    void setUp() {
        festivalDTO = new FestivalDTO(
                1L,
                "Mock Festival",
                "Amsterdam",
                "VEGAN",
                LocalDateTime.of(2025, 8, 15, 18, 0),
                100,
                BigDecimal.valueOf(29.99),
                0,
                List.of("Truck 1", "Truck 2")
        );
    }

    @Test
    @WithMockUser(username = "user")
    void testShowRegistrationForm() throws Exception {
        FestivalDTO festivalDTO = new FestivalDTO(
                1L,
                "Test Festival",
                "Test locatie",
                "BBQ",
                LocalDateTime.now().plusDays(1),
                100,
                new BigDecimal("25.00"),
                5,
                List.of("Truck A", "Truck B")
        );

        when(festivalService.findFestivalDTOById(1L)).thenReturn(festivalDTO);

        mockMvc.perform(MockMvcRequestBuilders.get("/register/1"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("festival"))
                .andExpect(model().attributeExists("registrationRequest"))
                .andExpect(view().name("festivalDetails"));
    }


    @Test
    @WithMockUser(username = "user")
    void testRegisterForFestival_Success() throws Exception {
        when(festivalService.findFestivalDTOById(1L)).thenReturn(festivalDTO);
        doNothing().when(registrationValidator).validate(any(), eq(1L), eq("user"), any());
        when(registrationService.registerForFestival(1L, "user", 2)).thenReturn("Successfully registered");

        mockMvc.perform(MockMvcRequestBuilders.post("/register/1")
                        .param("ticketsToBuy", "2")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("message"))
                .andExpect(model().attributeExists("ticketsBought"))
                .andExpect(view().name("registrationSuccess"));
    }

    @Test
    @WithMockUser(username = "user")
    void testRegisterForFestival_WithValidationErrors() throws Exception {
        when(festivalService.findFestivalDTOById(1L)).thenReturn(festivalDTO);

        doAnswer(invocation -> {
            BindingResult bindingResult = invocation.getArgument(3);
            bindingResult.reject("error.code", "Mock validation error");
            return null;
        }).when(registrationValidator).validate(any(), eq(1L), eq("user"), any());

        mockMvc.perform(MockMvcRequestBuilders.post("/register/1")
                        .param("ticketsToBuy", "2")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("errors"))
                .andExpect(model().attributeExists("festival"))
                .andExpect(view().name("festivalDetails"));
    }

    @Test
    void testRegisterForFestiival_NotAuther() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/register/1")
                        .param("ticketsToBuy", "2"))
                .andExpect(status().isForbidden());
    }
}
