package br.com.security.sso.service;

import br.com.security.sso.dto.UserDTO;
import br.com.security.sso.exceptionhandling.BusinessException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

public interface UserService extends AuthenticationProvider {

    UserDTO findByUsername(String email) throws BusinessException;

    Authentication authenticate(Authentication auth) throws AuthenticationException;

    boolean supports(Class<?> auth);

}