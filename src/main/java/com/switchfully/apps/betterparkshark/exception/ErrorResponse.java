package com.switchfully.apps.betterparkshark.exception;

public class ErrorResponse {
    private String message;
    private int status;

    public ErrorResponse(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public int getStatus() {
        return status;
    }
}
