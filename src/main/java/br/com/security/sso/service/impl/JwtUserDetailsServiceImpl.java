package br.com.security.sso.service.impl;

//import br.com.intelipost.security.iam.model.User;
//import br.com.intelipost.security.iam.repository.UserRepository;
import br.com.security.sso.service.JwtUserDetailsService;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailsServiceImpl implements UserDetailsService, JwtUserDetailsService {

    /*
    @Autowired private UserRepository userRepository;

    @Autowired private PasswordEncoder bcryptEncoder;
    */

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        /*
        User user = userRepository.findByEmailAndDeletedFalse(email).get();
        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + email);
        }
        return new org.springframework.security.core.userdetails.User(user.getEmail(), bcryptEncoder.encode(user.getPasswordHash()),
                new ArrayList<>());
        */

        return null;
    }

}