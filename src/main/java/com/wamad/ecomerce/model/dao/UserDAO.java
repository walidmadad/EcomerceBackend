package com.wamad.ecomerce.model.dao;

import com.wamad.ecomerce.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserDAO extends CrudRepository<User, Long> {
    Optional<User>  findByEmail(String email);
    Optional<User>  findByUsername(String username);
}
