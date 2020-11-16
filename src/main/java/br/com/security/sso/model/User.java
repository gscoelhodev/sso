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
@Entry(base = "ou=users,dc=sso,dc=security,dc=com,dc=br", objectClasses = { "user" })
public class User implements Serializable {

	private static final long serialVersionUID = -1024678991226448571L;

	@Id
    private Name id;

    @Attribute(name = "uid")
    private String login;

    @Attribute(name = "cn")
    private String commonName;

    @Attribute(name = "sn")
    private String surName;

    @Attribute(name = "group")
    private Name groupDn; //Distinguish Name

    @Transient
    private Group group;

    private Boolean enabled;

}