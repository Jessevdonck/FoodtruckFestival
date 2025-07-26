package org.foodtruckfestival.foodtruckfestival.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Registration
    {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int aantalTickets;

    @ManyToOne
    private MyUser user;

    @ManyToOne
    private Festival festival;

    private LocalDateTime registrationTime;
    }

