package br.com.security.sso.service.impl;

import br.com.security.sso.dto.UserDTO;
import br.com.security.sso.exceptionhandling.BusinessException;
import br.com.security.sso.exceptionhandling.Message;
import br.com.security.sso.service.UserService;
import br.com.security.sso.util.MessageError;
import java.util.Collection;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import static java.util.Objects.isNull;
import static br.com.security.sso.util.PasswordHelper.validatePassword;
import javax.transaction.Transactional;

@Service
@Transactional
@Slf4j
public class UserServiceImpl implements UserService {

    private List<Message> messages;

    @Override
    public UserDTO findByUsername(String email) throws BusinessException {
        log.info("method=findByUsername from class={}, message={}", UserServiceImpl.class.getName(), "service operation has started.");
        try {
            UserDTO userDTO = UserDTO.builder().id(Long.valueOf(1)).email("gscoelho.coelho@gmail.com").build();
            return userDTO;
        } catch (Exception e) {
            messages = new ArrayList<>();
            messages.add(Message.createMessage(MessageError.ERROR.message, e.getMessage(), MessageError.GENERAL_EXCEPTION.name()));
            throw new BusinessException(messages, new Object(){}.getClass().getEnclosingMethod().getName(), UserServiceImpl.class.getName());
        }
    }

    @Override
    public Authentication authenticate(Authentication auth) throws AuthenticationException {
        Authentication authentication = null;
        String username = auth.getName();
        String password = auth.getCredentials().toString();

        try {
            UserDTO user = findByUsername(username);

            if (isNull(user))
                throw new UsernameNotFoundException(MessageError.INVALID_CREDENTIALS.message);

            if (validatePassword(user, password)) {
                Collection<? extends GrantedAuthority> authorities = new ArrayList<>();
                authentication = new UsernamePasswordAuthenticationToken(username, password, authorities);
            } else
                throw new UsernameNotFoundException(MessageError.INVALID_CREDENTIALS.message);

        } catch (BusinessException e){
            throw new BadCredentialsException(MessageError.INVALID_CREDENTIALS.message);
        }

        return authentication;
    }

    @Override
    public boolean supports(Class<?> auth) {
        return auth.equals(UsernamePasswordAuthenticationToken.class);
    }

}