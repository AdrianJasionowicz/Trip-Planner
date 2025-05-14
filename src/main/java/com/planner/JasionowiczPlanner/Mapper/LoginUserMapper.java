package com.planner.JasionowiczPlanner.Mapper;

import com.planner.JasionowiczPlanner.LoginUser.LoginUser;
import com.planner.JasionowiczPlanner.LoginUser.LoginUserDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LoginUserMapper {
    LoginUserDTO toDto(LoginUser loginUser);
    LoginUser fromDto(LoginUserDTO loginUserDTO);
}
