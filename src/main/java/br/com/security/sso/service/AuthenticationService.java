package br.com.security.sso.service;

import br.com.security.sso.exceptionhandling.BusinessException;
import br.com.security.sso.model.UserApplication;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

public interface AuthenticationService {

    UserApplication authenticateOnLDAP(String username, String password, String appName) throws BusinessException;

    Authentication obtainIAMCredentials(Authentication auth) throws AuthenticationException;

    Authentication authenticateOnGoogleWorkspace(String username, String password) throws BusinessException;

}