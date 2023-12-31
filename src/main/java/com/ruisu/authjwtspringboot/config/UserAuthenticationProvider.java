package com.ruisu.authjwtspringboot.config;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.ruisu.authjwtspringboot.dtos.UserDto;
import com.ruisu.authjwtspringboot.entities.User;
import com.ruisu.authjwtspringboot.exceptions.AppException;
import com.ruisu.authjwtspringboot.mappers.UserMapper;
import com.ruisu.authjwtspringboot.repositories.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Collections;
import java.util.Date;

@RequiredArgsConstructor
@Component
public class UserAuthenticationProvider {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Value("${security:jwt:token:secret-key}")
    private String secretKey;

    @PostConstruct
    protected void init(){
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public String createToken(UserDto userDto){
        Date now = new Date();
        Date expiresDate = new Date(now.getTime() + 3_600_000);

        return JWT.create()
                .withIssuer(userDto.getUserName())
                .withIssuedAt(now)
                .withExpiresAt(expiresDate)
                .withClaim("firstName", userDto.getFirstName())
                .withClaim("lastName", userDto.getLastName())
                .sign(Algorithm.HMAC256(secretKey));

    }

    public Authentication validateToken(String token){

        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        JWTVerifier verifier = JWT.require(algorithm).build();

        DecodedJWT decodedJWT = verifier.verify(token);

        UserDto user = UserDto.builder()
                .userName(decodedJWT.getIssuer())
                .firstName(decodedJWT.getClaim("firstName").asString())
                .lastName(decodedJWT.getClaim("lastName").asString())
                .build();

        return new UsernamePasswordAuthenticationToken(user, null, Collections.emptyList());

    }

    public Authentication validateTokenStrongly(String token){

        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        JWTVerifier verifier = JWT.require(algorithm).build();

        DecodedJWT decodedJWT = verifier.verify(token);

        User user = userRepository.findByUserName(decodedJWT.getIssuer())
                .orElseThrow(()-> new AppException("Unknown user", HttpStatus.NOT_FOUND));

        return new UsernamePasswordAuthenticationToken(userMapper.toUserDto(user), null, Collections.emptyList());

    }



}
