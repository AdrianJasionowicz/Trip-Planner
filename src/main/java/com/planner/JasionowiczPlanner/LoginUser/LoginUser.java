package com.planner.JasionowiczPlanner.LoginUser;

import com.planner.JasionowiczPlanner.Trip.Trip;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    private String email;

    @OneToMany(mappedBy = "loginUser", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Trip> trips;

}
