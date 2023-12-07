package com.integrador.evently.users.service;

import com.integrador.evently.providers.dto.ProviderDTO;
import com.integrador.evently.providers.model.Provider;
import com.integrador.evently.providers.repository.ProviderRepository;
import com.integrador.evently.providers.service.ProviderService;
import com.integrador.evently.users.dto.RegisterUser;
import com.integrador.evently.users.dto.UserDto;
import com.integrador.evently.users.dto.UserLogin;
import com.integrador.evently.users.model.User;
import com.integrador.evently.users.model.UserType;
import com.integrador.evently.users.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    @Autowired
    ProviderService providerService;
    private final ModelMapper modelMapper;
    @Autowired
    private ProviderRepository providerRepository;

    public UserService(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(user -> modelMapper.map(user, UserDto.class))
                .collect(Collectors.toList());
    }

    public UserDto getUserById(Long userId) {
        return userRepository.findById(userId)
                .map(user -> modelMapper.map(user, UserDto.class))
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public List<UserDto> getUsersByType(String userType) {
        String upperUserType = userType.toUpperCase();
        if(!isValidUserType(upperUserType)) return Collections.emptyList();
        return userRepository.findByType(UserType.valueOf(upperUserType)).stream()
                .map(user -> modelMapper.map(user, UserDto.class))
                .collect(Collectors.toList());
    }

    public void deleteUserById(Long userId) {
        userRepository.deleteById(userId);
    }

    private boolean isValidUserType(String userTypeString) {
        for (UserType userType : UserType.values()) {
            if (userType.name().equalsIgnoreCase(userTypeString)) {
                return true;
            }
        }
        return false;
    }

    public UserDto createUser(RegisterUser user) {
        User newUser = modelMapper.map(user, User.class);
        UserDto userResponse = modelMapper.map(userRepository.save(newUser), UserDto.class);

        if (user.getType().equals(UserType.PROVIDER)) {
            ProviderDTO providerDTO = new ProviderDTO();
            providerDTO.setUser(userResponse);
            providerDTO.setName(user.getProviderName());
            providerDTO.setAddress(user.getProviderAddress());
            providerDTO.setInformation(user.getProviderInformation());
            providerDTO.setCategory(null);
            providerDTO.setProducts(null);
            ProviderDTO provider = providerService.saveProvider(providerDTO);
            userResponse.setProviderInfo(provider);
        }
        return userResponse;
    }

    public UserDto login(UserLogin credentials) {
        User user = userRepository.findByEmail(credentials.getEmail())
                .orElse(null);
        if (user != null && user.getPassword().equals(credentials.getPassword())) {
            UserDto dto = modelMapper.map(user, UserDto.class);
            if (dto.getType().equals(UserType.PROVIDER)) {
                Provider provider = providerRepository.findByUserId(dto.getId())
                        .orElseThrow(() -> new RuntimeException("Provider not found"));
                dto.setProviderInfo(modelMapper.map(provider, ProviderDTO.class));
            }
            return dto;
        }
        return null;
    }

    public UserDto updateUser(Long userId, UserDto user) {
        User userToUpdate = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        if (user.getUsername() != null) userToUpdate.setUsername(user.getUsername());
        if (user.getLastname() != null) userToUpdate.setLastname(user.getLastname());
        if (user.getEmail() != null) userToUpdate.setEmail(user.getEmail());
        if (user.getFirstname() != null) userToUpdate.setFirstname(user.getFirstname());

        if (UserType.PROVIDER.equals(user.getType())) {
            Provider provider = providerRepository.findByUserId(userId)
                    .orElseThrow(() -> new RuntimeException("Provider not found"));
            ProviderDTO providerDTO = user.getProviderInfo();
            if (providerDTO.getName() != null) provider.setName(providerDTO.getName());
            if (providerDTO.getAddress() != null) provider.setAddress(providerDTO.getAddress());
            if (providerDTO.getInformation() != null) provider.setInformation(providerDTO.getInformation());
            providerRepository.save(provider);
        }

        return modelMapper.map(userRepository.save(userToUpdate), UserDto.class);
    }
}
