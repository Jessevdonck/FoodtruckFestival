    package org.foodtruckfestival.foodtruckfestival.domain;

    import jakarta.persistence.*;
    import lombok.*;

    import java.time.LocalDateTime;

    @Entity
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    public class Registration
        {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        public int amountOfTickets;

        @ManyToOne
        @JoinColumn(name = "user_id")
        private MyUser user;

        @ManyToOne
        private Festival festival;

        private LocalDateTime registrationTime;
        }

