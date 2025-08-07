package org.foodtruckfestival.foodtruckfestival;

import org.foodtruckfestival.foodtruckfestival.domain.Festival;
import org.foodtruckfestival.foodtruckfestival.domain.MyUser;
import org.foodtruckfestival.foodtruckfestival.domain.Registration;
import org.foodtruckfestival.foodtruckfestival.dto.FestivalDTO;
import org.foodtruckfestival.foodtruckfestival.enums.Food;
import org.foodtruckfestival.foodtruckfestival.enums.Location;
import org.foodtruckfestival.foodtruckfestival.service.FestivalService;
import org.foodtruckfestival.foodtruckfestival.validator.FestivalCategoryConflictOnSameDayValidator;
import org.foodtruckfestival.foodtruckfestival.validator.FestivalNameDuplicateOnSameDayValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.validation.BindingResult;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class FestivalControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private FestivalService festivalService;

    @MockitoBean
    private FestivalCategoryConflictOnSameDayValidator categoryConflictOnSameDayValidator;

    @MockitoBean
    private FestivalNameDuplicateOnSameDayValidator nameDuplicateOnSameDayValidator;

    private Festival exampleFestival;
    private FestivalDTO exampleFestivalDTO;

    @BeforeEach
    void setup() {
        exampleFestival = new Festival();
        exampleFestival.setId(1L);
        exampleFestival.setName("Test Festival");
        exampleFestival.setDateTime(LocalDateTime.of(2025, 8, 7, 12, 0));
        exampleFestival.setLocation(Location.ANTWERPEN);
        exampleFestival.setCategorie(Food.VEGAN);
        exampleFestival.setPrice(new BigDecimal("20.00"));
        exampleFestival.setFoodtrucks(new ArrayList<>(List.of("T1", "T2", "T3")));
        exampleFestival.setFestivalCode1(200);
        exampleFestival.setFestivalCode2(300);

        exampleFestivalDTO = new FestivalDTO(
                exampleFestival.getId(),
                exampleFestival.getName(),
                exampleFestival.getLocation().name(),
                exampleFestival.getCategorie().name(),
                exampleFestival.getDateTime(),
                exampleFestival.getAvailableTickets(),
                exampleFestival.getPrice(),
                0,
                exampleFestival.getFoodtrucks());

    }

    @Test
    void testGetFestivals() throws Exception {
        when(festivalService.fetchFestivalOverview()).thenReturn(List.of(exampleFestivalDTO));

        mockMvc.perform(get("/festivals"))
                .andExpect(status().isOk())
                .andExpect(view().name("festivals"))
                .andExpect(model().attribute("festivals", hasSize(1)))
                .andExpect(model().attribute("festivals", hasItem(
                        hasProperty("name", is("Test Festival"))
                )));
    }

    @Test
    @WithMockUser(username = "user")
    void testFestivalDetails() throws Exception {
        when(festivalService.findFestivalDTOById(1L)).thenReturn(exampleFestivalDTO);

        mockMvc.perform(get("/festivals/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("festivalDetails"))
                .andExpect(model().attributeExists("festival"))
                .andExpect(model().attributeExists("registrationRequest"))
                .andExpect(model().attribute("festival", hasProperty("id", is(1L))));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testShowFestivalForm() throws Exception {
        mockMvc.perform(get("/festivals/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("festival-form"))
                .andExpect(model().attributeExists("festival"))
                .andExpect(model().attribute("locaties", arrayContaining(Location.values())))
                .andExpect(model().attribute("categorieen", arrayContaining(Food.values())));
    }

    @Test
    void testShowFestivalForm_NotAuthed() throws Exception {
        mockMvc.perform(get("/festivals/add"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("**/login"));
    }

    @Test
    @WithMockUser(username = "user")
    void testShowFestivalForm_NoPermission() throws Exception {
        mockMvc.perform(get("/festivals/add"))
                .andExpect(status().isForbidden());
    }


    @Test
    @WithMockUser(username = "adminm", roles = {"ADMIN"})
    void testShowEditFestivalForm() throws Exception {
        when(festivalService.findById(1L)).thenReturn(exampleFestival);

        mockMvc.perform(get("/festivals/edit/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("festival-form"))
                .andExpect(model().attribute("festival", hasProperty("id", is(1L))))
                .andExpect(model().attribute("locaties", arrayContaining(Location.values())))
                .andExpect(model().attribute("categorieen", arrayContaining(Food.values())));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testProcessFestivalForm_ValidData() throws Exception {
        doNothing().when(categoryConflictOnSameDayValidator).validate(any(), any());
        doNothing().when(nameDuplicateOnSameDayValidator).validate(any(), any());

        mockMvc.perform(post("/festivals/add")
                        .param("name", "New Festival")
                        .param("dateTime", "2025-09-12T12:00")
                        .param("location", "ANTWERPEN")
                        .param("categorie", "BBQ")
                        .param("price", "25.00")
                        .param("maxTickets", "100")
                        .param("festivalCode1", "200")
                        .param("festivalCode2", "300")
                        .param("foodtrucks[0]", "T1")
                        .param("foodtrucks[1]", "T2")
                        .param("foodtrucks[2]", "T3")
                        .with(csrf())
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/festivals"));

        verify(festivalService).save(any(Festival.class));
    }


    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testProcessFestivalForm_InvalidData() throws Exception {
        // Simuleer dat validator errors toevoegen
        doAnswer(invocation -> {
            Object target = invocation.getArgument(0);
            BindingResult bindingResult = invocation.getArgument(1);
            bindingResult.rejectValue("name", "error.name", "Duplicate festival name");
            return null;
        }).when(nameDuplicateOnSameDayValidator).validate(any(), any());

        doNothing().when(categoryConflictOnSameDayValidator).validate(any(), any());

        mockMvc.perform(post("/festivals/edit/1")
                        .param("name", "")
                        .param("dateTime", "2025-09-02T14:00")
                        .param("location", "KORTRIJK")
                        .param("categorie", "MEXICAANS")
                        .param("price", "25.00")
                        .param("maxTickets", "100")
                        .param("festivalCode1", "200")
                        .param("festivalCode2", "300")
                        .param("foodtrucks[0]", "T1")
                        .param("foodtrucks[1]", "T2")
                        .param("foodtrucks[2]", "T3")
                        .with(csrf())
                )
                .andExpect(status().isOk())
                .andExpect(view().name("festival-form"))
                .andExpect(model().attributeHasFieldErrors("festival", "name"))
                .andExpect(model().attribute("categorieen", arrayContaining(Food.values())))
                .andExpect(model().attribute("locaties", arrayContaining(Location.values())));

        verify(festivalService, never()).save(any());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testUpdateFestival_ValidData() throws Exception {
        doNothing().when(categoryConflictOnSameDayValidator).validate(any(), any());
        doNothing().when(nameDuplicateOnSameDayValidator).validate(any(), any());

        mockMvc.perform(post("/festivals/edit/1")
                        .param("name", "Updated Festival")
                        .param("location", Location.ANTWERPEN.toString())
                        .param("foodCategory", Food.VEGAN.toString())
                        .param("price", "20.00")
                        .param("dateTime", "2025-08-07T12:00")
                        .param("maxTickets", "100")
                        .param("festivalCode1", "200")
                        .param("festivalCode2", "300")
                        .param("foodtrucks[0]", "T1")
                        .param("foodtrucks[1]", "T2")
                        .param("foodtrucks[2]", "T3")
                        .with(csrf())
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/festivals"));

        verify(festivalService).updateFestival(eq(1L), any(Festival.class));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testUpdateFestival_InvalidData() throws Exception {
        doAnswer(invocation -> {
            BindingResult bindingResult = invocation.getArgument(1);
            bindingResult.rejectValue("name", "error.name", "Duplicate festival name");
            return null;
        }).when(nameDuplicateOnSameDayValidator).validate(any(), any());

        doNothing().when(categoryConflictOnSameDayValidator).validate(any(), any());

        mockMvc.perform(post("/festivals/edit/1")
                        .param("name", "")
                        .param("dateTime", "2025-08-07T12:00")
                        .param("location", "AMSTERDAM")
                        .param("categorie", "VEGAN")
                        .param("price", "20.00")
                        .param("maxTickets", "100")
                        .param("festivalCode1", "200")
                        .param("festivalCode2", "300")
                        .param("foodtrucks[0]", "T1")
                        .param("foodtrucks[1]", "T2")
                        .param("foodtrucks[2]", "T3")
                        .with(csrf())
                )
                .andExpect(status().isOk())
                .andExpect(view().name("festival-form"))
                .andExpect(model().attributeHasFieldErrors("festival", "name"))
                .andExpect(model().attribute("categorieen", arrayContaining(Food.values())))
                .andExpect(model().attribute("locaties", arrayContaining(Location.values())));

        verify(festivalService, never()).updateFestival(anyLong(), any());
    }
}
