package com.integrador.evently.users.controller;


import com.integrador.evently.users.dto.RegisterUser;
import com.integrador.evently.users.dto.UserDto;
import com.integrador.evently.users.dto.UserLogin;
import com.integrador.evently.users.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{userId}")
    public UserDto getUserById(@PathVariable Long userId) {
        return userService.getUserById(userId);
    }

    @GetMapping("/type/{userType}")
    public List<UserDto> getUserByType(@PathVariable String userType) {
        return userService.getUsersByType(userType);
    }

    @GetMapping
    public List<UserDto> getAllUsers() {
        return userService.getAllUsers();
    }

    @DeleteMapping("/{userId}")
    public void deleteUserById(@PathVariable Long userId) {
        userService.deleteUserById(userId);
    }

    @PostMapping("/register")
    public UserDto createUser(@RequestBody RegisterUser user) {
        return userService.createUser(user);
    }

    @PostMapping("/login")
    public ResponseEntity<UserDto> login(@RequestBody UserLogin credentials) {
        UserDto logged = userService.login(credentials);
        return logged != null ?
                new ResponseEntity<>(logged, HttpStatus.CREATED) :
                new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }
}
