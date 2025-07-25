package domain;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Registration {

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

