package br.com.security.sso.util;

public class Constant {

    public static final String API_VERSION = "/api/v1";

    public static final String COMMA_SEPARATOR = ",";
    public static final String SEMICOLON_SEPARATOR = ";";
    public static String WHITE_SPACE = " ";
    public static String AUTHORIZATION = "Authorization";
    public static final String SECRET_REFRESH_TOKEN = "jwt.secret-refresh-token";
    public static final Long EXPIRATION_TIME = 60L * 60_000L; //Where 60_000 is equals to 1 minute
    public static final Long REFRESH_TOKEN_EXPIRATION_TIME = 2 * EXPIRATION_TIME;
    public static final String BEARER = "bearer";
    public static final String HASH = "#";
    public static final String ORIGIN_SYSTEM = "MS-SSO";
    public static final String SECRET = "secretA321BC515E088";
    public static final String ANONYMOUS_USER = "anonymousUser";

}