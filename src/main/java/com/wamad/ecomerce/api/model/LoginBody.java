package com.wamad.ecomerce.api.model;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class LoginBody {
    private String username;

    private String email;

    private String password;

}
