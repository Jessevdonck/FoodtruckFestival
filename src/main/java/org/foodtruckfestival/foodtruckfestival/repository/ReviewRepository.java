package org.foodtruckfestival.foodtruckfestival.repository;

import org.foodtruckfestival.foodtruckfestival.domain.Festival;
import org.foodtruckfestival.foodtruckfestival.domain.MyUser;
import org.foodtruckfestival.foodtruckfestival.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long>
    {
        List<Review> findByFestivalOrderByScoreDesc(Festival festival);
        boolean existsByUserAndFestival(MyUser user, Festival festival);
        @Query("SELECT AVG(r.score) FROM Review r WHERE r.festival = :festival")
        Double findAverageScoreByFestival(@Param("festival") Festival festival);
    }