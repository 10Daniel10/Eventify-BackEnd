package com.test.ga;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;

@RestController
public class User {
    
    @GetMapping("/user")
    public Map<String, String> saludo(){
        return System.getenv();
    }
}
