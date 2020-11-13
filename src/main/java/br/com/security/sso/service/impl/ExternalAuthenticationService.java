package br.com.security.sso.service.impl;

import br.com.security.sso.exceptionhandling.BusinessException;
import br.com.security.sso.exceptionhandling.Message;
import br.com.security.sso.model.ApplicativeUser;
import br.com.security.sso.model.User;
import br.com.security.sso.repository.UserRepository;
import br.com.security.sso.repository.GroupRepository;
import br.com.security.sso.service.AuthenticationService;
import br.com.security.sso.util.MessageError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;
import javax.naming.Name;
import java.util.ArrayList;
import java.util.List;

@Service
public class ExternalAuthenticationService implements AuthenticationService {

    private List<Message> messages;

    @Autowired private UserRepository userRepository;
    @Autowired private GroupRepository groupRepository;
    @Autowired private LdapTemplate ldapTemplate;

    @Override
    public ApplicativeUser authenticateUser(String username, String password, String appName) throws BusinessException {
        try {
            User user = userRepository.findByLogin(username);

            boolean authenticate = ldapTemplate.authenticate(user.getId(), "(uid=" + username + ")", password);
            if (!authenticate)
                throw new BadCredentialsException("Authentication failed. Please check your login or password");

            getGroup(user);

            //TODO: Create mapper from object User to ApplicativeUser
            ApplicativeUser applicativeUser = ApplicativeUser.builder()
                    .username(user.getLogin())
                    .email(user.getEmail())
                    .group(user.getGroup())
                    .surName(user.getSurName()).build();

            return applicativeUser;
        } catch (Exception e) {
            messages = new ArrayList<>();
            messages.add(Message.createMessage(MessageError.ERROR.message, e.getMessage(), MessageError.GENERAL_EXCEPTION.name()));
            throw new BusinessException(messages, new Object(){}.getClass().getEnclosingMethod().getName(), ExternalAuthenticationService.class.getName());
        }
    }

    private void getGroup(User user) {
        Name siteDn = user.getGroupDn();
        groupRepository.findById(siteDn).ifPresent(user::setGroup);
    }

}