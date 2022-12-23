package com.example.restservice;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicLong;

@RestController
public class RegisterUserController {

    private final AtomicLong counter = new AtomicLong();

    @PostMapping("/registerUser")
    public RegisterUserResponse registerUser(
                                 @RequestParam(value = "email") String username,
                                 @RequestParam(value = "password") String pasword,
                                 @RequestParam(value = "name") String name
    ) {

        String url = "jdbc:mysql://localhost:3306/vizsga?serverTimezone=UTC";
        Adatbazis ab = new Adatbazis(url, "root", "");
        int hiba = ab.insertIntoUserTable(username, pasword, name);
        if (hiba == 0 ) {
            return new RegisterUserResponse(counter.incrementAndGet(), "Sikeres küldés és tárolás", hiba);
        } else {
            return new RegisterUserResponse(counter.incrementAndGet(), "Sikeres küldés, de hibás tárolás",hiba);
        }
    }
}