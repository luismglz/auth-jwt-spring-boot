package com.ruisu.authjwtspringboot.repositories;

import com.ruisu.authjwtspringboot.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUserName(String userName);

}
