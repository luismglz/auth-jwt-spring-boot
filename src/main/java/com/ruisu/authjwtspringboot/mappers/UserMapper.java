package com.ruisu.authjwtspringboot.mappers;

import com.ruisu.authjwtspringboot.dto.UserDto;
import com.ruisu.authjwtspringboot.entities.User;
import org.mapstruct.Mapper;

@Mapper(componentModel="spring")
public interface UserMapper {

    UserDto toUserDto(User user);
}
