package br.com.security.sso.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.ldap.odm.annotations.Attribute;
import org.springframework.ldap.odm.annotations.Entry;
import org.springframework.ldap.odm.annotations.Id;
import org.springframework.ldap.odm.annotations.Transient;
import javax.naming.Name;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entry(base = "ou=users,dc=workspace,dc=com", objectClasses = { "user" })
public class User implements Serializable {

	private static final long serialVersionUID = -1024678991226448571L;

	@Id
    private Name id;

    @Attribute(name = "uid")
    private String login;

    @Attribute(name = "mail")
    private String email;

    @Attribute(name = "sn")
    private String surName;

    @Attribute(name = "group")
    private Name groupDn;

    @Transient
    private Group group;

}