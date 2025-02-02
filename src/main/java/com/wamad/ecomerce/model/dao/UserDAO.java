package com.wamad.ecomerce.model.dao;

import com.wamad.ecomerce.model.User;
import org.springframework.data.repository.ListCrudRepository;

import java.util.Optional;

public interface UserDAO extends ListCrudRepository<User, Long> {
    Optional<User>  findByEmail(String email);
    Optional<User>  findByUsername(String username);
}
