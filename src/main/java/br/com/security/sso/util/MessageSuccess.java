package br.com.security.sso.util;

public enum MessageSuccess {

    //General success
    SUCCESS("SUCCESS");

    public String message;

    MessageSuccess(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

}