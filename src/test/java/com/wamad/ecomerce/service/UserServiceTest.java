package com.wamad.ecomerce.service;

import com.icegreen.greenmail.configuration.GreenMailConfiguration;
import com.icegreen.greenmail.junit5.GreenMailExtension;
import com.icegreen.greenmail.util.ServerSetupTest;
import com.wamad.ecomerce.api.model.LoginBody;
import com.wamad.ecomerce.api.model.RegistrationBody;
import com.wamad.ecomerce.exception.EmailFaiureException;
import com.wamad.ecomerce.exception.UserAlreadyExistException;
import com.wamad.ecomerce.exception.UserNotVerifiedException;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserServiceTest {

    @RegisterExtension
    private static GreenMailExtension greenMailExtension = new GreenMailExtension(ServerSetupTest.SMTP)
            .withConfiguration(GreenMailConfiguration.aConfig().withUser("springboot", "secret"))
            .withPerMethodLifecycle(true);

    @Autowired
    private UserService userService;

    @Test
    @Transactional
    public void testRegisterUser() throws MessagingException {
        RegistrationBody body = new RegistrationBody();
        body.setUsername("UserA");
        body.setEmail("UserServiceTest@.com");
        body.setFirstName("FirstName");
        body.setLastName("LastName");
        body.setPassword("MyPassword123");
        Assertions.assertThrows(UserAlreadyExistException.class,
                () -> userService.registerUser(body), "Username should already be in use.");
        body.setUsername("UserServiceTest");
        body.setEmail("UserA@test.com");
        Assertions.assertThrows(UserAlreadyExistException.class,
                () -> userService.registerUser(body), "Email should already be in use.");
        body.setEmail("UserServiceTestr@test.com");
        Assertions.assertDoesNotThrow(() -> userService.registerUser(body),
                "User should register successfully.");
        Assertions.assertEquals(body.getEmail(), greenMailExtension.getReceivedMessages()[0]
                .getRecipients(Message.RecipientType.TO)[0].toString());
    }

    @Test
    @Transactional
    public void testLoginUser() throws UserNotVerifiedException, EmailFaiureException {
        LoginBody loginBody = new LoginBody();
        loginBody.setUsername("UserA11");
        loginBody.setPassword("PasswordA123");
        Assertions.assertNull(userService.loginUser(loginBody), "Username not exist.");

        loginBody.setEmail("UserABB@test.com");
        loginBody.setPassword("PasswordA123");
        Assertions.assertNull(userService.loginUser(loginBody), "Username not exist.");

        loginBody.setUsername("UserA");
        loginBody.setPassword("PasswordA123");
        Assertions.assertNotNull(userService.loginUser(loginBody), "Login Successfully.");

        loginBody.setUsername("UserAB");
        loginBody.setEmail("UserA@test.com");
        Assertions.assertNotNull(userService.loginUser(loginBody), "Login Successfully.");

        loginBody.setUsername("UserB");
        loginBody.setEmail("userB@test.com");
        loginBody.setPassword("PasswordB123");

        try{
            userService.loginUser(loginBody);
            Assertions.assertTrue(false, "Email not verified.");
        }catch(UserNotVerifiedException ex){
            Assertions.assertTrue(ex.isNewEmailSent(), "Email Verification should be sent.");
            Assertions.assertEquals(1, greenMailExtension.getReceivedMessages().length);
        }

        try{
            userService.loginUser(loginBody);
            Assertions.assertTrue(false, "Email not verified.");
        }catch(UserNotVerifiedException ex){
            Assertions.assertFalse(ex.isNewEmailSent(), "Email Verification should not be sent.");
            Assertions.assertEquals(1, greenMailExtension.getReceivedMessages().length);
        }

    }

}