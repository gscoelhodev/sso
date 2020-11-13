package br.com.security.sso.repository;

import br.com.security.sso.model.User;
import org.springframework.data.ldap.repository.LdapRepository;

public interface UserRepository extends LdapRepository<User>  {

    User findByLogin(String login);

}