package com.wamad.ecomerce.dto;

import com.wamad.ecomerce.enums.UserRole;
import lombok.Data;

@Data
public class UserDto {
    private Long id;
    private String email;
    private String name;
    private UserRole userRole;
}
