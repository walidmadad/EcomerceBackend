package com.wamad.ecomerce.service;

import com.wamad.ecomerce.api.model.LoginBody;
import com.wamad.ecomerce.api.model.RegistrationBody;
import com.wamad.ecomerce.exception.EmailFaiureException;
import com.wamad.ecomerce.exception.UserAlreadyExistException;
import com.wamad.ecomerce.exception.UserNotVerifiedException;
import com.wamad.ecomerce.model.User;
import com.wamad.ecomerce.model.VerificationToken;
import com.wamad.ecomerce.model.dao.UserDAO;
import com.wamad.ecomerce.model.dao.VerificationTokenDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Optional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserDAO userDAO;
    private final EncryptionService encryptionService;
    private final JWTService jwtService;
    private final EmailService emailService;
    private final VerificationTokenDAO verificationTokenDAO;

    public User registerUser(RegistrationBody registrationBody) throws UserAlreadyExistException, EmailFaiureException {
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
        VerificationToken verificationToken = createVerificationToken(user);
        emailService.sendVerificationEmail(verificationToken);
        return userDAO.save(user);
    }

    private VerificationToken createVerificationToken(User user){
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(jwtService.generateJWT(user));
        verificationToken.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        verificationToken.setUser(user);
        user.getVerficationTokens().add(verificationToken);
        return verificationToken;
    }

    public String loginUser(LoginBody loginBody) throws UserNotVerifiedException, EmailFaiureException {
        Optional<User> userOptional = userDAO.findByEmail(loginBody.getEmail());
        if(userOptional.isEmpty()){
            userOptional = userDAO.findByUsername(loginBody.getUsername());
        }
        if(userOptional.isPresent()){
            User user = userOptional.get();
            if(encryptionService.checkPassword(loginBody.getPassword(), user.getPassword())){
                if(user.isEmailVerified()) {
                    return jwtService.generateJWT(user);
                } else{
                    List<VerificationToken> verificationTokenList = user.getVerficationTokens();
                    boolean resend = verificationTokenList.size() == 0 ||
                            verificationTokenList.get(0).getCreatedAt().before(new Timestamp(System.currentTimeMillis() - (60 * 60 * 1000)));
                    if(resend){
                        VerificationToken verificationToken = createVerificationToken(user);
                        verificationTokenDAO.save(verificationToken);
                        emailService.sendVerificationEmail(verificationToken);
                    }
                    throw new UserNotVerifiedException(resend);
                }
            }
        }
        return null;
    }
}
