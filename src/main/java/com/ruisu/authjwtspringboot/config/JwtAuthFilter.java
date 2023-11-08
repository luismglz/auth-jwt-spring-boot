package com.ruisu.authjwtspringboot.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;

@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final UserAuthenticationProvider userAuthenticationProvider;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String header = request.getHeader(HttpHeaders.AUTHORIZATION);

        if(header != null){

            //separate "Bearer" and token in a String array
            String[] bearerToken = header.split(" ");

            if(bearerToken.length == 2 && "Bearer".equals(bearerToken[0])){
                try{
                    if("GET".equals(request.getMethod())){
                        SecurityContextHolder.getContext().setAuthentication(userAuthenticationProvider.validateToken(bearerToken[1]));
                    }else{
                        SecurityContextHolder.getContext().setAuthentication(userAuthenticationProvider.validateTokenStrongly(bearerToken[1]));
                    }


                }catch (RuntimeException exception){
                    SecurityContextHolder.clearContext();
                    throw exception;
                }
            }
        }

        filterChain.doFilter(request, response);
    }
}
