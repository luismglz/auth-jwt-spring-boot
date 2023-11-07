package com.ruisu.authjwtspringboot.mappers;

import com.ruisu.authjwtspringboot.dtos.SignUpDto;
import com.ruisu.authjwtspringboot.dtos.UserDto;
import com.ruisu.authjwtspringboot.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel="spring")
public interface UserMapper {
    UserDto toUserDto(User user);

    @Mapping(target = "password", ignore = true)
    User signUpToUser(SignUpDto signUpDto);
}
