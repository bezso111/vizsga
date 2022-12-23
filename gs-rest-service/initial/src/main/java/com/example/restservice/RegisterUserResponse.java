package com.example.restservice;

public class RegisterUserResponse {

    private final long id;
    private final String content;

    public RegisterUserResponse(long id, String content) {
        this.id = id;
        this.content = content;
    }

    public long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }
}