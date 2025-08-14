package org.foodtruckfestival.foodtruckfestival.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.foodtruckfestival.foodtruckfestival.enums.Food;
import org.foodtruckfestival.foodtruckfestival.enums.Location;
import org.foodtruckfestival.foodtruckfestival.utils.LocalDateTimeDeserializer;
import org.foodtruckfestival.foodtruckfestival.utils.LocalDateTimeSerializer;
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
@ValidFestivalDate
@JsonPropertyOrder({
        "id",
        "name",
        "foodtrucks",
        "location",
        "categorie",
        "dateTime",
        "festivalCode1",
        "festivalCode2",
        "maxTickets",
        "price",
        "availableTickets",
        "registrations",
        "reviews"
})
public class Festival
    {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "{festival.name.blank}")
    @Pattern(regexp = "^[A-Za-z]{3,}.*", message = "{festival.name.invalid}")
    private String name;

    @ElementCollection
    @NotEmpty
    @NoDuplicateFoodtrucks
    @Size(max = 4)
    private List<String> foodtrucks;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "{festival.name.invalid}")
    private Location location;

    @Enumerated(EnumType.STRING)
    private Food categorie;

    @NotNull(message = "{festival.date.blank}")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime dateTime;

    private int festivalCode1;
    private int festivalCode2;

    @Min(value = 50, message = "{festival.tickets.min}")
    @Max(value = 250, message = "{festival.tickets.max}")
    private int maxTickets;

    @NotNull(message = "{festival.price.blank}")
    @ValidPrice
    private BigDecimal price;

    @OneToMany(mappedBy = "festival")
    private List<Registration> registrations;

    @OneToMany(mappedBy = "festival")
    private List<Review> reviews;

    @Transient
    public int getAvailableTickets() {
        if (registrations == null) return maxTickets;
        int soldTickets = registrations.stream().mapToInt(Registration::getAmountOfTickets).sum();
        return maxTickets - soldTickets;
    }
}

