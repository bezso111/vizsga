package com.example.restservice;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicLong;

@RestController
public class BefektetesController {
    private final AtomicLong counter = new AtomicLong();

    @PostMapping("/befektetes")
    public BefektetesResponse Befektetes(
            @RequestParam(value = "oszeg") String oszeg,
            @RequestParam(value = "futamido") String futamido,
            @RequestParam(value = "befektetes") String befektetes,
            @RequestParam(value = "userid") String userid,
            @RequestParam(value = "username") String username
    ) {
        boolean bef;
        String hiba = "";
        String url = "jdbc:mysql://localhost:3306/vizsga?serverTimezone=UTC";
        Adatbazis ab = new Adatbazis(url, "root", "");
        if (befektetes.equalsIgnoreCase("befektetes")) {
            bef = true;
        } else if (befektetes.equalsIgnoreCase("hitel")) {
            bef = false;
        } else {
            System.out.println("Hibás eljárás hívás!! '" + befektetes + "'");
            throw new RuntimeException("Hibás hívás!! '" + befektetes + "'");
        }
        System.out.println("proba!!!!");
        if (ab.testuder(userid, username)) {
            hiba = ab.insertBefektetes(oszeg, futamido, bef, userid);
        } else {
            System.out.println("Nem használható user!!");
            throw new RuntimeException("Nem használható user!!");
        }
        System.out.println(hiba);
        System.out.println(ab.errorcode);
        return new BefektetesResponse(counter.incrementAndGet(), ab.errorcode, hiba);
    }
}





