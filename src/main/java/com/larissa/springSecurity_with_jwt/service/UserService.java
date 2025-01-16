package com.larissa.springSecurity_with_jwt.service;

import com.larissa.springSecurity_with_jwt.dto.CreateUserDto;
import com.larissa.springSecurity_with_jwt.dto.LoginUserDto;
import com.larissa.springSecurity_with_jwt.dto.RecoveryJwtTokenDto;
import com.larissa.springSecurity_with_jwt.entity.User;
import com.larissa.springSecurity_with_jwt.repository.UserRepository;
import com.larissa.springSecurity_with_jwt.security.authentication.JwtTokenService;
import com.larissa.springSecurity_with_jwt.security.config.SecurityConfiguration;
import com.larissa.springSecurity_with_jwt.security.userDetails.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenService jwtTokenService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SecurityConfiguration securityConfiguration;

    public RecoveryJwtTokenDto authenticateUser(LoginUserDto loginUserDto) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(loginUserDto.email(), loginUserDto.password());

        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        return new RecoveryJwtTokenDto(jwtTokenService.generateToken(userDetails));
    }

    public void createUser(CreateUserDto createUserDto) {
        User newUser = new User(
                createUserDto.email(),
                securityConfiguration.passwordEncoder().encode(createUserDto.password()),
                Collections.singleton(createUserDto.role())
        );

        userRepository.save(newUser);
    }
}
