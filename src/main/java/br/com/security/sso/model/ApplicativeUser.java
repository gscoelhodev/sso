package br.com.security.sso.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonDeserialize
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApplicativeUser implements Serializable, UserDetails {

    private static final long serialVersionUID = -2024675991223445582L;

    private String username;

    private String email;

    @JsonIgnore
    private String password;

    private boolean degrade;

    private String job;

    private String perId;

    private String login;

    private Group group;

    private String firstName;

    private String surName;

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() { return true; }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() { return true; }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() { return true; }

    @JsonIgnore
    @Override
    public boolean isEnabled() { return true; }

    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() { return List.of(); }

}