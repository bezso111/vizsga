package com.example.restservice;

public class RegisterUserResponse {

    private final long id;
    private final String content;
    private final int errorcode;

    public RegisterUserResponse(long id, String content, int errorcode) {
        this.id = id;
        this.content = content;
        this.errorcode = errorcode;
    }

    public long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public int getErrorcode() {
        return errorcode;
    }
}