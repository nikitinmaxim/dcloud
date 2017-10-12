package ru.drip.config;

public class SessionControlBlock {

    private String accessToken;

    public SessionControlBlock() {
        this.accessToken = null;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
