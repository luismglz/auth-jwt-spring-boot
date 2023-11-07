package com.ruisu.authjwtspringboot.controllers;

import com.ruisu.authjwtspringboot.dto.CredentialsDto;
import com.ruisu.authjwtspringboot.dto.UserDto;
import com.ruisu.authjwtspringboot.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<UserDto> login(@RequestBody CredentialsDto credentialsDto){
        UserDto user = userService.login(credentialsDto);
        return ResponseEntity.ok(user);
    }
}
