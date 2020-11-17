package br.com.security.sso.service.impl;

import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;
import br.com.security.sso.exceptionhandling.BusinessException;
import br.com.security.sso.model.UserApplication;
import br.com.security.sso.service.AuthenticationService;
import br.com.security.sso.service.LDAPService;
import br.com.security.sso.service.UserService;

@Service
@Transactional
public class AuthenticationServiceImpl implements AuthenticationService {

    @Autowired private LDAPService ldapService;
    @Autowired private UserService userService;

    @Override
    public UserApplication authenticateOnLDAP(String username, String password, String appName) throws BusinessException {
        return ldapService.authenticate(username, password, appName);
    }

    @Override
    public Authentication obtainIAMCredentials(Authentication auth) throws AuthenticationException {
        return userService.authenticate(auth);
    }

    @Override
    public Authentication authenticateOnGoogleWorkspace(String username, String password) throws BusinessException {
        return null;
    }

}