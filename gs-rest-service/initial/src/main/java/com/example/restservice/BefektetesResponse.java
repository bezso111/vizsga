package com.example.restservice;

public class BefektetesResponse {
    private final long id;
    private final int errorcode;
    private final String error;

    public BefektetesResponse(long id, int errorcode, String error) {
        this.id = id;
        this.errorcode = errorcode;
        this.error = error;
    }

    public long getId() {
        return id;
    }

    public int getErrorcode() {
        return errorcode;
    }

    public String getError() {
        return error;
    }
}
