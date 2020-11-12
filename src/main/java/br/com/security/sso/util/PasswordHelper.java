package br.com.security.sso.util;

import br.com.security.sso.dto.UserDTO;

public class PasswordHelper {

    public static Boolean validatePassword(final UserDTO user, final String password) {
        if (user.getPassword().equals(password)) return true;

        return false;
    }

}