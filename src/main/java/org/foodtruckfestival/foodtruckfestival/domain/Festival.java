package org.foodtruckfestival.foodtruckfestival.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Festival
    {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    @ElementCollection
    private List<String> foodtrucks; // max 4, validatie nodig

    @NotBlank
    private String location;

    @Enumerated(EnumType.STRING)
    private Food categorie;

    private LocalDateTime dateTime;

    private int festivalCode1;
    private int festivalCode2;

    private int maxTickets;

    private BigDecimal price;

    @OneToMany(mappedBy = "festival")
    private List<Registration> registrations;

    @OneToMany(mappedBy = "festival")
    private List<Review> reviews;
    }

