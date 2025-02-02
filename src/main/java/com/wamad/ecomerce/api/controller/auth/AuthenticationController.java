package com.wamad.ecomerce.api.controller.auth;

import com.wamad.ecomerce.api.model.LoginBody;
import com.wamad.ecomerce.api.model.LoginResponse;
import com.wamad.ecomerce.api.model.RegistrationBody;
import com.wamad.ecomerce.exception.EmailFaiureException;
import com.wamad.ecomerce.exception.UserAlreadyExistException;
import com.wamad.ecomerce.exception.UserNotVerifiedException;
import com.wamad.ecomerce.model.User;
import com.wamad.ecomerce.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity registerUser(@Valid @RequestBody RegistrationBody registrationBody){
        try {
            userService.registerUser(registrationBody);
            return ResponseEntity.ok().build();
        } catch (UserAlreadyExistException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        } catch (EmailFaiureException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/login")
    public ResponseEntity loginUser(@Valid @RequestBody LoginBody loginBody){
        String jwt = null;
        try {
            jwt = userService.loginUser(loginBody);
        } catch (UserNotVerifiedException e) {
            LoginResponse loginResponse = new LoginResponse();
            loginResponse.setSuccess(false);
            String reason = "USER_NOT_VERIFIED";
            if (e.isNewEmailSent()){
                reason += "_EMAIL_RESENT";
            }
            loginResponse.setMessage(reason);
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(loginResponse);
        } catch (EmailFaiureException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        if (jwt == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }else{
            LoginResponse loginResponse = new LoginResponse();
            loginResponse.setJwt(jwt);
            loginResponse.setSuccess(true);
            return ResponseEntity.ok(loginResponse);
        }
    }

    @PostMapping("/verify")
    public ResponseEntity verifyEmail(@RequestParam String token){
        if(userService.verifyUser(token)){
            return ResponseEntity.ok().build();
        }else {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @GetMapping("/me")
    public User getLoggedInUserProfile(@AuthenticationPrincipal User user) {
        return user;
    }
}
