package com.planner.JasionowiczPlanner.LoginUser;

import com.planner.JasionowiczPlanner.Mapper.LoginUserMapper;
import com.planner.JasionowiczPlanner.Trip.Trip;
import jakarta.persistence.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LoginUserService {
    private LoginUserRepository loginUserRepository;
    private LoginUserMapper loginUserMapper;
    private PasswordEncoder passwordEncoder;

    public LoginUserService(LoginUserRepository loginUserRepository, LoginUserMapper loginUserMapper, PasswordEncoder passwordEncoder) {
        this.loginUserRepository = loginUserRepository;
        this.loginUserMapper = loginUserMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public List<LoginUserDTO> getAllUsers() {
      List<LoginUser> loginUser = loginUserRepository.findAll();
        return loginUser
                .stream()
                .map(loginUserMapper::toDto)
                .collect(Collectors.toList());
    }


    public LoginUserDTO getLoginUserByUserId(Long id) {
        return loginUserMapper.toDto(loginUserRepository.getReferenceById(id));
    }

    public void updateUser(Long id, LoginUserDTO dto) {
        LoginUser loginUser = loginUserRepository.getReferenceById(id);
        if (dto.getUsername() != null && !dto.getUsername().equals(loginUser.getUsername())) {
            if (!checkIsUserNameUnique(dto.getUsername())) {
                throw new IllegalArgumentException("Username already taken!");
            }
            loginUser.setUsername(dto.getUsername());
        }
        dto.setPassword(passwordEncoder.encode(dto.getPassword()));
        Optional.ofNullable(loginUser.getPassword()).ifPresent(loginUser::setPassword);
        Optional.ofNullable(loginUser.getEmail()).ifPresent(loginUser::setEmail);
        Optional.ofNullable(loginUser.getTrips()).ifPresent(loginUser::setTrips);
        loginUserRepository.save(loginUser);
    }

    public boolean checkIsUserNameUnique(String username) {
        return !loginUserRepository.existsByUsername(username);

    }
    public LoginUserDTO getCurrentUser(Authentication auth) {
         LoginUser loginUser = (LoginUser) auth.getPrincipal();
         LoginUserDTO userDTO = loginUserMapper.toDto(loginUser);
                return userDTO;
    }

    public void setUserRoleToAdmin(Long id) {
        LoginUser loginUser = loginUserRepository.getReferenceById(id);
        loginUser.setRole(LoginUserRole.ROLE_ADMIN);
        loginUserRepository.save(loginUser);
    }

    public void createNewLoginUser(LoginUserDTO dto) {
        LoginUser loginUser = new LoginUser();
        loginUser.setUsername(dto.getUsername());
        loginUser.setPassword(passwordEncoder.encode(dto.getPassword()));
        loginUser.setName(dto.getName());
        loginUser.setEmail(dto.getEmail());
        loginUser.setRole(LoginUserRole.ROLE_USER);
        loginUser.setTrips(null);
        loginUserRepository.save(loginUser);

    }
}
