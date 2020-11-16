package br.com.security.sso.service.impl;

import br.com.security.sso.exceptionhandling.BusinessException;
import br.com.security.sso.exceptionhandling.Message;
import br.com.security.sso.mapper.UserMapper;
import br.com.security.sso.model.UserApplication;
import br.com.security.sso.model.User;
import br.com.security.sso.repository.UserRepository;
import br.com.security.sso.repository.GroupRepository;
import br.com.security.sso.service.AuthenticationService;
import br.com.security.sso.util.MessageError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import javax.naming.Name;
import java.util.ArrayList;
import java.util.List;
import static java.util.Objects.isNull;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private List<Message> messages;

    @Autowired private UserRepository userRepository;
    @Autowired private GroupRepository groupRepository;
    @Autowired private LdapTemplate ldapTemplate;

    @Override
    public UserApplication authenticate(String username, String password, String appName) throws BusinessException {
        final User user = findByLogin(username);
        try {
            boolean authenticate = ldapTemplate.authenticate(user.getId(), "(uid=" + username + ")", password);
            if (!authenticate)
                throw new BadCredentialsException(MessageError.AUTHENTICATION_FAILED.message);

            setGroup(user);

            UserApplication userApplication = UserMapper.fromEntityToUserApplication(user);

            if(!userApplication.isEnabled())
                throw new DisabledException(MessageError.USER_DISABLED.message);

            return userApplication;
        } catch (BadCredentialsException e) {
            messages = new ArrayList<>();
            messages.add(Message.createMessage(MessageError.ERROR.message, e.getMessage(), MessageError.INVALID_CREDENTIALS.message));
            throw new BusinessException(messages, new Object(){}.getClass().getEnclosingMethod().getName(), AuthenticationServiceImpl.class.getName());
        }
    }

    private User findByLogin(String username) throws BusinessException {
        try {
            final User user = userRepository.findByLogin(username);
            if(isNull(user))
                throw new UsernameNotFoundException(MessageError.USER_NOT_FOUND.message);
            return user;
        } catch (Exception e) {
            messages = new ArrayList<>();
            messages.add(Message.createMessage(MessageError.ERROR.message, e.getMessage(), MessageError.GENERAL_EXCEPTION.message));
            throw new BusinessException(messages, new Object(){}.getClass().getEnclosingMethod().getName(), AuthenticationServiceImpl.class.getName());
        }
    }

    private void setGroup(User user) throws BusinessException {
        try {
            Name siteDn = user.getGroupDn();
            groupRepository.findById(siteDn).ifPresent(user::setGroup);
        } catch (Exception e) {
            messages = new ArrayList<>();
            messages.add(Message.createMessage(MessageError.ERROR.message, e.getMessage(), MessageError.GENERAL_EXCEPTION.message));
            throw new BusinessException(messages, new Object(){}.getClass().getEnclosingMethod().getName(), AuthenticationServiceImpl.class.getName());
        }
    }

}