package org.foodtruckfestival.foodtruckfestival.repository;

import org.foodtruckfestival.foodtruckfestival.domain.Festival;
import org.foodtruckfestival.foodtruckfestival.domain.MyUser;
import org.foodtruckfestival.foodtruckfestival.domain.Registration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface RegistrationRepository extends JpaRepository<Registration, Long>
    {
        int countByFestival(Festival festival);

        @Query("SELECT SUM(r.amountOfTickets) FROM Registration r WHERE r.user = :user AND r.festival.dateTime BETWEEN :start AND :end")
        Integer totalTicketsByUserInPeriod(MyUser user, LocalDateTime start, LocalDateTime end);

        @Query("SELECT COALESCE(SUM(r.amountOfTickets), 0) FROM Registration r WHERE r.user = :user AND r.festival = :festival")
        int totalTicketsForFestivalByUser(@Param("user") MyUser user, @Param("festival") Festival festival);

        int countByFestivalAndUser(Festival festival, MyUser user);

        List<Registration> findByUser(MyUser user);
    }
