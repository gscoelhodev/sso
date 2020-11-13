package br.com.security.sso.service;

import br.com.security.sso.exceptionhandling.BusinessException;
import br.com.security.sso.model.ApplicativeUser;

public interface AuthenticationService {

    ApplicativeUser authenticateUser(String username, String password, String appName) throws BusinessException;

}