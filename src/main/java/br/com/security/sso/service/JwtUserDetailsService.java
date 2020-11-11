package br.com.security.sso.service;

//import br.com.intelipost.security.iam.dto.UserDTO;
//import br.com.intelipost.security.iam.model.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface JwtUserDetailsService {

    UserDetails loadUserByUsername(String email) throws UsernameNotFoundException;

}