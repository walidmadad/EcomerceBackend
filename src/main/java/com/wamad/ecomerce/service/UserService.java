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
    private final EncryptionService encryptionService;

    public User registerUser(RegistrationBody registrationBody) throws UserAlreadyExistException{
        if(userDAO.findByEmail(registrationBody.getEmail()).isPresent() || userDAO.findByUsername(registrationBody.getUsername()).isPresent()){
            throw new UserAlreadyExistException();
        }
        User user = new User();
        user.setUsername(registrationBody.getUsername());
        user.setEmail(registrationBody.getEmail());
        user.setFirstName(registrationBody.getFirstName());
        user.setLastName(registrationBody.getLastName());

        String encryptedPassword = encryptionService.encryptPassword(registrationBody.getPassword());
        user.setPassword(encryptedPassword);
        return userDAO.save(user);
    }
}
