package com.wamad.ecomerce.model.dao;

import com.wamad.ecomerce.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserDAO extends CrudRepository<User, Long> {

}
