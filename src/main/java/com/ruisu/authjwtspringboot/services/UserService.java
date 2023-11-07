package com.ruisu.authjwtspringboot.services;

import com.ruisu.authjwtspringboot.dto.CredentialsDto;
import com.ruisu.authjwtspringboot.dto.UserDto;
import com.ruisu.authjwtspringboot.entities.User;
import com.ruisu.authjwtspringboot.exceptions.AppException;
import com.ruisu.authjwtspringboot.mappers.UserMapper;
import com.ruisu.authjwtspringboot.repositories.UserRepository;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.CharBuffer;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;


    public UserDto login(CredentialsDto credentialsDto){
        User user = userRepository.findByUserName(credentialsDto.userName())
                .orElseThrow(() -> new AppException("Unknown user", HttpStatus.NOT_FOUND));

        if(passwordEncoder.matches(CharBuffer.wrap(credentialsDto.password()),user.getPassword())){
            return userMapper.toUserDto(user);
        }

        throw new AppException("Invalid password", HttpStatus.BAD_REQUEST);

    }

}
