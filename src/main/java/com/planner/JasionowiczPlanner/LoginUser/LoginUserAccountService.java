package com.planner.JasionowiczPlanner.LoginUser;

import com.planner.JasionowiczPlanner.Config.JwtUtil;
import com.planner.JasionowiczPlanner.Trip.Trip;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class LoginUserAccountService {

    private JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private LoginUserRepository loginUserRepository;
    private LoginUserService loginUserService;



    public LoginUserAccountService(JwtUtil jwtUtil, AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder, LoginUserRepository loginUserRepository) {
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.loginUserRepository = loginUserRepository;
    }

    public void updateUser(Long id, LoginUserDTO dto) {
        LoginUser loginUser = loginUserRepository.getReferenceById(id);
        if (dto.getUsername() != null && !dto.getUsername().equals(loginUser.getUsername())) {
            if (!loginUserService.checkIsUserNameUnique(dto.getUsername())) {
                throw new IllegalArgumentException("Username already taken!");
            }
            loginUser.setUsername(dto.getUsername());
        }
        if (dto.getPassword() != null && !dto.getPassword().isBlank()) {
            loginUser.setPassword(passwordEncoder.encode(dto.getPassword()));
        }
        Optional.ofNullable(loginUser.getEmail()).ifPresent(loginUser::setEmail);
        Optional.ofNullable(loginUser.getTrips()).ifPresent(loginUser::setTrips);
        loginUserRepository.save(loginUser);
    }

    public void createNewLoginUser(LoginUserDTO dto) {
        LoginUser loginUser = new LoginUser();
        loginUser.setUsername(dto.getUsername());
        loginUser.setPassword(passwordEncoder.encode(dto.getPassword()));
        loginUser.setName(dto.getName());
        loginUser.setEmail(dto.getEmail());
        loginUser.setRole(LoginUserRole.ROLE_USER);
        List <Trip> trip = new ArrayList<>();
        loginUser.setTrips(trip);
        loginUserRepository.save(loginUser);

    }

    public String loginAndGetToken(LoginRequestDto dto) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword())
            );
        } catch (Exception e) {
            throw new BadCredentialsException("Nieprawidłowe dane logowania");
        }

        LoginUser user = loginUserRepository.findByUsername(dto.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("Użytkownik nie istnieje"));
        List<String> roles = List.of(user.getRole().name());

        return jwtUtil.generateToken(user.getUsername(),roles);
    }
}
