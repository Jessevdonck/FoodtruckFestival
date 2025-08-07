package org.foodtruckfestival.foodtruckfestival;

import org.foodtruckfestival.foodtruckfestival.domain.Festival;
import org.foodtruckfestival.foodtruckfestival.domain.MyUser;
import org.foodtruckfestival.foodtruckfestival.domain.Review;
import org.foodtruckfestival.foodtruckfestival.service.FestivalService;
import org.foodtruckfestival.foodtruckfestival.service.MyUserService;
import org.foodtruckfestival.foodtruckfestival.service.ReviewService;
import org.foodtruckfestival.foodtruckfestival.validator.ReviewPermissionValidator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ReviewControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private FestivalService festivalService;

    @MockitoBean
    private ReviewService reviewService;

    @MockitoBean
    private MyUserService myUserService;

    @MockitoBean
    private ReviewPermissionValidator reviewPermissionValidator;

    @Test
    @WithMockUser(username = "user")
    void showReviewForm_WhenUserHasPermission_ShouldReturnForm() throws Exception {
        Festival festival = new Festival();
        festival.setId(1L);
        MyUser user = new MyUser();

        when(festivalService.findById(1L)).thenReturn(festival);
        when(myUserService.findByUsername("user")).thenReturn(user);
        when(reviewPermissionValidator.canUserWriteReview(user, festival)).thenReturn(true);

        mockMvc.perform(get("/reviews/1/add"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("festival"))
                .andExpect(model().attributeExists("review"))
                .andExpect(view().name("review-form"));
    }

    @Test
    @WithMockUser(username = "user")
    void showReviewForm_WhenUserHasNoPermission_ShouldRedirect() throws Exception {
        Festival festival = new Festival();
        festival.setId(1L);
        MyUser user = new MyUser();

        when(festivalService.findById(1L)).thenReturn(festival);
        when(myUserService.findByUsername("user")).thenReturn(user);
        when(reviewPermissionValidator.canUserWriteReview(user, festival)).thenReturn(false);

        mockMvc.perform(get("/reviews/1/add"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/reviews/1?error=notAllowed"));
    }

    @Test
    @WithMockUser(username = "user")
    void submitReview_ShouldSaveReviewAndRedirect() throws Exception {
        Festival festival = new Festival();
        festival.setId(1L);
        MyUser user = new MyUser();

        when(festivalService.findById(1L)).thenReturn(festival);
        when(myUserService.findByUsername("user")).thenReturn(user);

        mockMvc.perform(post("/reviews/1/add")
                        .param("score", "4")
                        .param("description", "Great festival!")
                        .with(csrf())
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/reviews/1"));

        verify(reviewService, times(1)).save(any(Review.class));
    }

    @Test
    @WithMockUser(username = "testuser")
    void submitReview_ShouldFailValidation_WhenDescriptionBlank() throws Exception {
        Festival festival = new Festival();
        festival.setId(1L);
        MyUser user = new MyUser();

        when(festivalService.findById(1L)).thenReturn(festival);
        when(myUserService.findByUsername("testuser")).thenReturn(user);

        mockMvc.perform(post("/reviews/1/add")
                        .param("score", "4")
                        .param("description", "   ")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(model().attributeHasFieldErrors("review", "description"))
                .andExpect(view().name("review-form"));

        verify(reviewService, never()).save(any(Review.class));
    }

    @Test
    @WithMockUser(username = "user")
    void viewReviews_ShouldReturnReviewsPage() throws Exception {
        Festival festival = new Festival();
        festival.setId(1L);
        MyUser user = new MyUser();

        when(festivalService.findById(1L)).thenReturn(festival);
        when(reviewService.getReviewsForFestival(festival)).thenReturn(List.of());
        when(reviewService.getAverageScoreForFestival(festival)).thenReturn(4.5);
        when(myUserService.findByUsername("user")).thenReturn(user);
        when(reviewPermissionValidator.canUserWriteReview(user, festival)).thenReturn(true);

        mockMvc.perform(get("/reviews/1"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("festival"))
                .andExpect(model().attributeExists("reviews"))
                .andExpect(model().attributeExists("averageScore"))
                .andExpect(model().attribute("averageScore", 4.5))
                .andExpect(model().attribute("canWriteReview", true))
                .andExpect(view().name("reviews"));
    }

    @Test
    void showReviewForm_WithoutAuth_ShouldReturn403() throws Exception {
        mockMvc.perform(get("/reviews/1/add"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("**/login"));
    }

    @Test
    void submitReview_WithoutAuth_ShouldReturn403() throws Exception {
        mockMvc.perform(post("/reviews/1/add")
                        .param("score", "4")
                        .param("description", "Unauthorized review"))
                .andExpect(status().isForbidden());
    }

    @Test
    void viewReviews_WithoutAuth_ShouldRedirectToLogin() throws Exception {
        mockMvc.perform(get("/reviews/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("**/login"));
    }
}
