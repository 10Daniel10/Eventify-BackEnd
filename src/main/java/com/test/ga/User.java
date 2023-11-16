package com.test.ga;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class User {

    private VarConfig varConfig;

    User(VarConfig varConfig) {
        this.varConfig = varConfig;
    }

    @GetMapping("/user")
    public String saludo(){
        return varConfig.getDbUrl();
    }
}
