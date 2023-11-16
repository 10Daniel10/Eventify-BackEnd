package com.test.ga;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;

@RestController
public class User {

    private VarConfig varConfig;

    User(VarConfig varConfig) {
        this.varConfig = varConfig;
    }

    @GetMapping("/user")
    public Map<String, String> saludo(){
        return System.getenv();
    }
}
