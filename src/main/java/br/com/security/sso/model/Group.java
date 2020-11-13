package br.com.security.sso.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.ldap.odm.annotations.Attribute;
import org.springframework.ldap.odm.annotations.Entry;
import org.springframework.ldap.odm.annotations.Id;
import javax.naming.Name;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entry(base = "ou=groups", objectClasses = { "top" })
public class Group implements Serializable {

	private static final long serialVersionUID = -2737031814073628553L;

	@Id
    private Name id;

    @Attribute(name = "cn")
    private String commonName;

}