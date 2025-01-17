package com.larissa.springSecurity_with_jwt.dto;

import com.larissa.springSecurity_with_jwt.entity.UserRole;

public record RegisterUserDTO(String login, String password, UserRole role){
}
