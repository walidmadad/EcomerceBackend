package com.wamad.ecomerce.api.controller.auth;

import com.wamad.ecomerce.api.model.RegistrationBody;
import com.wamad.ecomerce.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final UserService userService;

    @PostMapping("/register")
    public void registerUser(@RequestBody RegistrationBody registrationBody){
        userService.registerUser(registrationBody);
    }
}
