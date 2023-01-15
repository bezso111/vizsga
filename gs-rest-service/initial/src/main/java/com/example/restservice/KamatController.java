package com.example.restservice;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@RestController
public class KamatController {

    private final AtomicLong counter = new AtomicLong();

    @PostMapping("/kamat")
    public KamatResponse kamat(

    ) {

        String url = "jdbc:mysql://localhost:3306/vizsga?serverTimezone=UTC";
        Adatbazis ab = new Adatbazis(url, "root", "");
        UserSelect contents = ab.selectKamat();
        int hiba = ab.errorcode;
        return new KamatResponse(counter.incrementAndGet(), contents.id, contents.name, hiba);
    }
}
