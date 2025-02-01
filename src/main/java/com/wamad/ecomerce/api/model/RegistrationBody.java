package com.wamad.ecomerce.api.model;

import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
public class RegistrationBody {
    private String username;

    @Email
    private String email;

    private String password;
    private String firstName;
    private String lastName;
}
