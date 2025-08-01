package org.foodtruckfestival.foodtruckfestival.repository;

import org.foodtruckfestival.foodtruckfestival.domain.Festival;
import org.foodtruckfestival.foodtruckfestival.enums.Food;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface FestivalRepository extends JpaRepository<Festival, Long>
    {
    @Query("SELECT f.maxTickets - COALESCE(SUM(r.aantalTickets), 0) FROM Festival f LEFT JOIN Registration r ON r.festival = f WHERE f.id = :festivalId GROUP BY f.id")
    Integer findAvailableTicketsByFestivalId(@Param("festivalId") Long festivalId);
    boolean existsByNameAndDateTimeBetween(String name, LocalDateTime start, LocalDateTime end);
    boolean existsByCategorieAndDateTimeBetween(Food categorie, LocalDateTime start, LocalDateTime end);
    }
