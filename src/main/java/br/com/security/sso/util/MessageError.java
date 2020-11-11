package br.com.security.sso.util;

public enum MessageError  {

    //User
    USER_NOT_FOUND("User not found"),

    //Auth
    AUTHENTICATION_REQUIRED("Usuário não autenticado para realizar a operação"),
    AUTHENTICATION_REFRESH_TOKEN_EXPIRED("Expired Refresh token "),
    AUTHENTICATION_UNDEFINED("Authentication undefined due to authorization requirements"),
    AUTHENTICATION_NOT_PERMISSION("You are not allowed to perform this action."),
    
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