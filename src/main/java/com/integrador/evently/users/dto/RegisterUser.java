package com.integrador.evently.users.dto;

import com.integrador.evently.users.model.UserType;
import jakarta.annotation.Nullable;
import lombok.Data;

@Data
public class RegisterUser {
    private String email;
    private String firstname;
    private String lastname;
    private UserType type;
    private String password;
    private String username;
    @Nullable
    private String providerAddress;
    @Nullable
    private String providerInformation;
    @Nullable
    private String providerName;
}
