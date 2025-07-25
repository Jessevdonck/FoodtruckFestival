package domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

@Entity
public class Review {

@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;

@Min(1)
@Max(5)
private int score;

@NotBlank
private String description;

@ManyToOne
private MyUser user;

@ManyToOne
private Festival festival;

private LocalDateTime postedAt;
}

