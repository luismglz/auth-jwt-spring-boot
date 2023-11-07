package com.ruisu.authjwtspringboot.controllers;

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

    @PostMapping("/login")
    public ResponseEntity<UserDto> login(@RequestBody CredentialsDto credentialsDto){
        UserDto user = userService.login(credentialsDto);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/signup")
    public ResponseEntity<UserDto> register(@RequestBody SignUpDto signUpDto){
        UserDto user = userService.signup(signUpDto);
        return ResponseEntity
                .created(URI.create("/users/"+user.getId()))
                .body(user);
    }
}
