package br.com.security.sso.repository;

import br.com.security.sso.model.Group;
import org.springframework.data.ldap.repository.LdapRepository;
import java.util.List;

public interface GroupRepository extends LdapRepository<Group> {

    List<Group> findAll();

}