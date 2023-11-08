package com.ruisu.authjwtspringboot.controllers;

import com.ruisu.authjwtspringboot.config.UserAuthenticationProvider;
import com.ruisu.authjwtspringboot.dtos.CredentialsDto;
import com.ruisu.authjwtspringboot.dtos.SignUpDto;
import com.ruisu.authjwtspringboot.dtos.UserDto;
import com.ruisu.authjwtspringboot.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;


@RestController
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final UserAuthenticationProvider userAuthenticationProvider;

    @PostMapping("/login")
    public ResponseEntity<UserDto> login(@RequestBody CredentialsDto credentialsDto){
        UserDto user = userService.login(credentialsDto);
        user.setToken(userAuthenticationProvider.createToken(user));
        return ResponseEntity.ok(user);
    }

    @PostMapping("/signup")
    public ResponseEntity<UserDto> register(@RequestBody SignUpDto signUpDto){
        UserDto createdUser = userService.signup(signUpDto);
        createdUser.setToken(userAuthenticationProvider.createToken(createdUser));
        return ResponseEntity
                .created(URI.create("/users/"+createdUser.getId()))
                .body(createdUser);
    }
}
