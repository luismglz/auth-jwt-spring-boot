package com.ruisu.authjwtspringboot.dtos;

public record SignUpDto(
        String firstName,
        String lastName,
        String userName,
        char[] password) {

}
