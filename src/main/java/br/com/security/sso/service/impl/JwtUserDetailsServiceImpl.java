package br.com.security.sso.service.impl;

import br.com.security.sso.dto.UserDTO;
import br.com.security.sso.service.JwtUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import org.springframework.security.crypto.password.PasswordEncoder;
import static java.util.Objects.isNull;

@Service
public class JwtUserDetailsServiceImpl implements UserDetailsService, JwtUserDetailsService {

    @Autowired private PasswordEncoder bcryptEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserDTO user = UserDTO.builder().id(Long.valueOf(1)).email("gscoelho.coelho@gmail.com").password("12345aA#").build();

        if (isNull(user))  throw new UsernameNotFoundException("User not found with username: " + email);

        return new org.springframework.security.core.userdetails.User(user.getEmail(), bcryptEncoder.encode(user.getPassword()), new ArrayList<>());
    }

}