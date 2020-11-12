package br.com.security.sso.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface JwtUserDetailsService {

    UserDetails loadUserByUsername(String email) throws UsernameNotFoundException;

}