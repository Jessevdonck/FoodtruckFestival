package org.foodtruckfestival.foodtruckfestival.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.foodtruckfestival.foodtruckfestival.enums.Food;
import org.foodtruckfestival.foodtruckfestival.validator.NoDuplicateFoodtrucks;
import org.foodtruckfestival.foodtruckfestival.validator.ValidFestivalCodes;
import org.foodtruckfestival.foodtruckfestival.validator.ValidFestivalDate;
import org.foodtruckfestival.foodtruckfestival.validator.ValidPrice;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ValidFestivalCodes
@ValidFestivalDate(start = "2025-07-01", end = "2025-07-31")
public class Festival
    {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Festival naam is verplicht")
    @Pattern(regexp = "^[A-Za-z]{3,}.*", message = "Naam moet beginnen met minstens drie letters")
    private String name;

    @ElementCollection
    @NotEmpty
    @NoDuplicateFoodtrucks
    @Size(max = 4)
    private List<@NotBlank(message = "Foodtruck veld mag niet leeg zijn") String> foodtrucks;

    @NotBlank
    private String location;

    @Enumerated(EnumType.STRING)
    private Food categorie;

    @NotNull(message = "Datum is verplicht")
    private LocalDateTime dateTime;

    private int festivalCode1;
    private int festivalCode2;

    @Min(value = 50, message = "Minstens 50 tickets")
    @Max(value = 250, message = "Maximum 250 tickets")
    private int maxTickets;

    @NotNull(message = "Prijs is verplicht")
    @ValidPrice
    private BigDecimal price;

    @OneToMany(mappedBy = "festival")
    private List<Registration> registrations;

    @OneToMany(mappedBy = "festival")
    private List<Review> reviews;
    }

