package com.planner.JasionowiczPlanner.LoginUser;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class LoginUserController {

    private final LoginUserService loginUserService;

    public LoginUserController(LoginUserService loginUserService) {
        this.loginUserService = loginUserService;
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
            loginUserService.updateUser(id,dto);
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
    @PreAuthorize("isAuthenticated()")
    public LoginUserDTO getCurrentUser(Authentication auth) {
        LoginUserDTO userDTO = loginUserService.getCurrentUser(auth);
        return userDTO;
    }

    @PostMapping("/register")
    public void register(@RequestBody LoginUserDTO dto) {
            loginUserService.createNewLoginUser(dto);
    }


    @PostMapping("/promote/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void promoteToAdmin(@PathVariable Long id) {
        loginUserService.setUserRoleToAdmin(id);
    }

}
