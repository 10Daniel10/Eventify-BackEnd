package com.test.ga;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class User {

    @GetMapping("/user")
    public String saludo(){
        return "Eventify!...";
    }
}
