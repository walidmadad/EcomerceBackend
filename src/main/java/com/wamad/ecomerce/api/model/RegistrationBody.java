package com.wamad.ecomerce.api.model;

import lombok.Data;

@Data
public class RegistrationBody {
    private String username;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
}
