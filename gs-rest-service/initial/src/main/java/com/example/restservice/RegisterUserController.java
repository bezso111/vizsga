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
                                 @RequestParam(value = "vezeteknev") String vezeteknev,
                                 @RequestParam(value = "keresztnev") String keresztnev,
                                 @RequestParam(value = "szuletesiev") String szuletesinev
    ) {

        String url = "jdbc:mysql://localhost:3306/mydb?serverTimezone=UTC";
        Adatbazis ab = new Adatbazis(url, "root", "");
        if (ab.insertIntoUserTable(vezeteknev, keresztnev, szuletesinev)) {
            return new RegisterUserResponse(counter.incrementAndGet(), "Sikeres küldés és tárolás");
        } else {
            return new RegisterUserResponse(counter.incrementAndGet(), "Sikeres küldés, de rossz tárolás!");
        }
    }
}