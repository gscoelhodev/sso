package br.com.security.sso.util;

public enum MessageError  {

    //User
    USER_NOT_FOUND("User not found"),
    USER_DISABLED("User disabled"),

    //Auth
    AUTHENTICATION_REQUIRED("Authentication required to perform this operation"),
    AUTHENTICATION_REFRESH_TOKEN_EXPIRED("Expired Refresh token"),
    AUTHENTICATION_UNDEFINED("Authentication undefined due to authorization requirements"),
    AUTHENTICATION_NOT_PERMISSION("You are not allowed to perform this action."),
    AUTHENTICATION_FAILED("Authentication failed. Please check your login or password"),
    AUTHENTICATION_REQUIRED_GOOGLE_OAUTH2("Full authentication is required to access this resource"),

    //General error
    ERROR("ERROR"),
    WARNING("WARNING"),
    GENERAL_EXCEPTION("General Exception"),
    INVALID_CREDENTIALS("Invalid credentials"),
    PASSWORD_INVALID("Password invalid"),
    USERNAME_INVALID("Username invalid");

    public String message;

    MessageError(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

}