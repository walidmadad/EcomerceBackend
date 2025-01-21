package com.wamad.ecomerce.services.auth;

import com.wamad.ecomerce.dto.SignupRequest;
import com.wamad.ecomerce.dto.UserDto;

public interface AuthService {
    UserDto creatUser(SignupRequest signupRequest);

    boolean hasUserWithEmail(String email);
}
