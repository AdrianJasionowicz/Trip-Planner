package com.planner.JasionowiczPlanner.Mapper;

import com.planner.JasionowiczPlanner.LoginUser.LoginUser;
import com.planner.JasionowiczPlanner.LoginUser.LoginUserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface LoginUserMapper {
    @Mapping(target = "trips", ignore = true)
    LoginUserDTO toDto(LoginUser loginUser);
    LoginUser fromDto(LoginUserDTO loginUserDTO);
}
