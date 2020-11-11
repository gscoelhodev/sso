package br.com.security.sso.controller;

//import br.com.intelipost.security.iam.dto.UserDTO;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
public class ApplicationController {

	public SecurityContext getContextInstance() {
		return SecurityContextHolder.getContext();
	}

	/*
	public UserDTO getContextUser(){
		return (UserDTO) getContextInstance().getAuthentication().getCredentials();
	}
	*/

}