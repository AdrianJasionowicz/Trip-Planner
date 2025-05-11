package com.planner.JasionowiczPlanner.LoginUser;

import org.springframework.stereotype.Service;

@Service
public class LoginUserService {
    private LoginUserRepository loginUserRepository;

    public LoginUserService(LoginUserRepository loginUserRepository) {
        this.loginUserRepository = loginUserRepository;
    }
}
