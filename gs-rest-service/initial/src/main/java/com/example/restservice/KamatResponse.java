package com.example.restservice;

public class KamatResponse {
    private final long id;
    private final int kamat;
    private final String error;
    private final int errorcode;

    public KamatResponse(long id, int kamat, String error, int errorcode) {
        this.id = id;
        this.kamat = kamat;
        this.error = error;
        this.errorcode = errorcode;
    }

    public long getId() {
        return id;
    }

    public String getError() {
        return error;
    }

    public int getKamat() {
        return kamat;
    }

    public int getErrorcode() {
        return errorcode;
    }
}
