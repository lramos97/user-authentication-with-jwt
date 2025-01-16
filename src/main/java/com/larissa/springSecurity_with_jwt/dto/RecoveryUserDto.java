package com.larissa.springSecurity_with_jwt.dto;

import com.larissa.springSecurity_with_jwt.entity.UserRole;

import java.util.List;

public record RecoveryUserDto(Long id, String email, List<UserRole> roles) {
}
