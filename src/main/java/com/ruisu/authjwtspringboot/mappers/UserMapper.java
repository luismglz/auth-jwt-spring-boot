package com.ruisu.authjwtspringboot.mappers;

import com.ruisu.authjwtspringboot.dtos.UserDto;
import com.ruisu.authjwtspringboot.entities.User;
import org.mapstruct.Mapper;
@Mapper(componentModel="spring")
public interface UserMapper {
    UserDto toUserDto(User user);
}
