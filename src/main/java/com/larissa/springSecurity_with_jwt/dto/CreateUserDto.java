package com.larissa.springSecurity_with_jwt.dto;

import com.larissa.springSecurity_with_jwt.entity.UserRole;

public record CreateUserDto(String email, String password, UserRole role){
}
