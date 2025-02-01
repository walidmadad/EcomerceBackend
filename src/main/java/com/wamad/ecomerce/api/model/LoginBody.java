package com.wamad.ecomerce.api.model;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class LoginBody {
    @NotEmpty
    @NotEmpty
    private String username;

    @NotEmpty
    @NotEmpty
    private String email;

    @NotEmpty
    @NotEmpty
    private String password;

}
