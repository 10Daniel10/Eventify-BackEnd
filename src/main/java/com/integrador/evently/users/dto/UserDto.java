package com.integrador.evently.users.dto;
import com.integrador.evently.providers.dto.ProviderDTO;
import com.integrador.evently.users.model.UserType;
import jakarta.annotation.Nullable;
import lombok.Data;

@Data
public class UserDto {
    private Long id;
    private String username;
    private String email;
    private String firstname;
    private String lastname;
    private UserType type;
    private String avatar;
    private ProviderDTO providerInfo;
}
