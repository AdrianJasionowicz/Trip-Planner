package com.planner.JasionowiczPlanner.LoginUser;

import com.planner.JasionowiczPlanner.Trip.Trip;
import com.planner.JasionowiczPlanner.Trip.TripDTO;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginUserDTO {

    private Long id;
    private String username;
    private String password;
    private String email;
    private List<TripDTO> trips;
    private String name;
    private LoginUserRole role;

}
