package org.foodtruckfestival.foodtruckfestival.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Review
    {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Min(1)
    @Max(5)
    private int score;

    @NotBlank(message = "{review.description.blank}")
    private String description;

    @ManyToOne
    private MyUser user;

    @ManyToOne
    @JsonBackReference
    private Festival festival;

    private LocalDateTime postedAt;
    }

