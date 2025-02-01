package com.wamad.ecomerce.service;

import com.wamad.ecomerce.api.model.RegistrationBody;
import com.wamad.ecomerce.exception.UserAlreadyExistException;
import com.wamad.ecomerce.model.User;
import com.wamad.ecomerce.model.dao.UserDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserDAO userDAO;

    public User registerUser(RegistrationBody registrationBody) {
        User user = new User();
        user.setUsername(registrationBody.getUsername());
        user.setEmail(registrationBody.getEmail());
        user.setFirstName(registrationBody.getFirstName());
        user.setLastName(registrationBody.getLastName());

        // TODO: Encrypt password
        user.setPassword(registrationBody.getPassword());
        return userDAO.save(user);
    }
}
