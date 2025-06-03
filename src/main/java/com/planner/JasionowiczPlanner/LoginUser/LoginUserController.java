package com.planner.JasionowiczPlanner.LoginUser;

import com.planner.JasionowiczPlanner.Mapper.LoginUserMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class LoginUserController {

    private final LoginUserService loginUserService;
    private LoginUserAccountService loginUserAccountService;
    private LoginUserMapper loginUserMapper;

    public LoginUserController(LoginUserService loginUserService, LoginUserAccountService loginUserAccountService, LoginUserMapper loginUserMapper) {
        this.loginUserService = loginUserService;
        this.loginUserAccountService = loginUserAccountService;
        this.loginUserMapper = loginUserMapper;
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<LoginUserDTO>> getAllUsers() {
       List<LoginUserDTO> loginUserDTOS = loginUserService.getAllUsers();
        return loginUserDTOS.isEmpty()
                ? ResponseEntity.noContent().header("Access-Control-Allow-Origin", "*").build()
                : ResponseEntity.ok(loginUserDTOS);
    }

    @GetMapping("/{id}")
    @PreAuthorize("#id == authentication.principal.id or hasRole('ADMIN')")
    public ResponseEntity<LoginUserDTO> getUserById(@PathVariable Long id) {
          LoginUserDTO loginUserDTO =  loginUserService.getLoginUserByUserId(id);
           return ResponseEntity
                   .status(HttpStatus.ACCEPTED)
                   .header("Hello there")
                   .body(loginUserDTO);
    }

    @PatchMapping("/{id}")
    @PreAuthorize("#id == authentication.principal.id")
    public ResponseEntity<String> updateUser(@PathVariable Long id, @RequestBody LoginUserDTO dto) {
            loginUserAccountService.updateUser(id,dto);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("#id == authentication.principal.id or hasRole('ADMIN')")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .header("User deleted")
                .build();
    }

    @GetMapping("/me")
    public ResponseEntity<LoginUserDTO> getMe() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        LoginUser user = loginUserService.findByUsernameWithTrips(username);

        return ResponseEntity.ok(loginUserMapper.toDto(user));
    }
    @GetMapping("/check")
    public ResponseEntity<?> checkAuth(Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }


    @PostMapping("/promote/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void promoteToAdmin(@PathVariable Long id) {
        loginUserService.setUserRoleToAdmin(id);
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody LoginUserDTO dto) {
        if (!loginUserService.checkIsUserNameUnique(dto.getUsername())) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body("Username is already in use");
        }
        loginUserAccountService.createNewLoginUser(dto);


        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body("User registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequestDto dto) {
        try {
            String token = loginUserAccountService.loginAndGetToken(dto);

            ResponseCookie cookie = ResponseCookie.from("jwt", token)
                    .httpOnly(true)
                    .secure(false)
                    .path("/")
                    .maxAge(24 * 60 * 60)
                    .sameSite("Strict")
                    .build();

            return ResponseEntity.ok()
                    .header(HttpHeaders.SET_COOKIE, cookie.toString())
                    .body(Map.of("message", "Zalogowano"));
        } catch (BadCredentialsException | UsernameNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }




}
