package com.planner.JasionowiczPlanner.LoginUser;

import com.planner.JasionowiczPlanner.Mapper.LoginUserMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Service
public class LoginUserService implements UserDetailsService {

    private LoginUserRepository loginUserRepository;
    private LoginUserMapper loginUserMapper;

    public LoginUserService(LoginUserRepository loginUserRepository, LoginUserMapper loginUserMapper) {
        this.loginUserRepository = loginUserRepository;
        this.loginUserMapper = loginUserMapper;
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



    public boolean checkIsUserNameUnique(String username) {
        return !loginUserRepository.existsByUsername(username);
    }
    public LoginUserDTO getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser user = (LoginUser) authentication.getPrincipal();
        return loginUserMapper.toDto(user);
    }

    public void setUserRoleToAdmin(Long id) {
        LoginUser loginUser = loginUserRepository.getReferenceById(id);
        loginUser.setRole(LoginUserRole.ROLE_ADMIN);
        loginUserRepository.save(loginUser);
    }

    public LoginUser findByUsernameWithTrips(String username) {
        return loginUserRepository.findByUsernameFetchTrips(username)
                .orElseThrow(() -> new UsernameNotFoundException("Nie znaleziono użytkownika"));
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return loginUserRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }


}
