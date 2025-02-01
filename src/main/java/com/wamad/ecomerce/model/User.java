package com.wamad.ecomerce.model;

import com.wamad.ecomerce.enums.UserRole;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;
    private String password;
    private String name;
    private UserRole role;

    @Lob
    @Column(columnDefinition = "longblob")
    private byte[] img;
}
