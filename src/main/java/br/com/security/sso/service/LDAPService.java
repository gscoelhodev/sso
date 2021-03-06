package br.com.security.sso.service;

import br.com.security.sso.exceptionhandling.BusinessException;
import br.com.security.sso.model.UserApplication;

public interface LDAPService {

    UserApplication authenticate(String username, String password, String appName) throws BusinessException;

}
