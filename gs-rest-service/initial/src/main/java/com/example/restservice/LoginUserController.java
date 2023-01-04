package com.example.restservice;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@RestController
public class LoginUserController {

    private final AtomicLong counter = new AtomicLong();

    @PostMapping("/loginUser")
    public LoginUserResponse loginUser(
            @RequestParam(value = "user") String username,
            @RequestParam(value = "password") String password
    ) {

        String url = "jdbc:mysql://localhost:3306/vizsga?serverTimezone=UTC";
        Adatbazis ab = new Adatbazis(url, "root", "");
        UserSelect contents = ab.selectLogin(username,password);
        int hiba = ab.errorcode;
        return new LoginUserResponse(counter.incrementAndGet(), contents.name, contents.id, hiba);
    }
}
