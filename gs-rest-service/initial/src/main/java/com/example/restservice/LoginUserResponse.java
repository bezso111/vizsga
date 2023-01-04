package com.example.restservice;

public class LoginUserResponse {
    private final long id;
    private final String name;
    private final int azon;
    private final int errorcode;

    public LoginUserResponse(long id, String name, int azon, int errorcode) {
        this.id = id;
        this.name = name;
        this.azon = azon;
        this.errorcode = errorcode;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAzon() {
        return azon;
    }

    public int getErrorcode() {
        return errorcode;
    }
}
