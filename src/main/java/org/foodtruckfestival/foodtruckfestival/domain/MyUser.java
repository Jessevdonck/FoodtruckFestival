package org.foodtruckfestival.foodtruckfestival.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.foodtruckfestival.foodtruckfestival.enums.Role;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "username")
@Table(name = "user")
@ToString
public class MyUser implements Serializable {
private static final long serialVersionUID = 1L;

@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long userId;

@Column(nullable = false, unique = true)
private String username;

@JsonIgnore
@Column(nullable = false)
private String password;

@Enumerated(EnumType.STRING)
@Column(length = 20)
private Role role;

@JsonIgnore
@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
private List<Registration> registrations = new ArrayList<>();

}