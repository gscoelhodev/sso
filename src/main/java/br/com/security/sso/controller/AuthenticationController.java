package br.com.security.sso.controller;

import br.com.security.sso.dto.JwtRequest;
import br.com.security.sso.dto.RefreshTokenRequestDTO;
import br.com.security.sso.model.UserApplication;
import br.com.security.sso.service.AuthenticationService;
import br.com.security.sso.service.UserService;
import br.com.security.sso.util.Constant;
import br.com.security.sso.exceptionhandling.ValidationException;
import br.com.security.sso.util.MessageError;
import br.com.security.sso.exceptionhandling.ExceptionResponse;
import br.com.security.sso.exceptionhandling.Message;
import br.com.security.sso.service.JwtUserDetailsService;
import br.com.security.sso.dto.JwtDTO;
import br.com.security.sso.config.JwtTokenUtil;
import java.util.List;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.HashMap;
import java.util.Map;
import static java.util.Objects.isNull;

@RequestMapping(Constant.API_VERSION)
@RestController
@Api(tags = "AUTH 0.1")
public class AuthenticationController extends ApplicationController {

	private Map<String, String> errorMessages;
	private List<Message> messages;
	private ExceptionResponse exceptionResponse;

	@Autowired private JwtTokenUtil jwtTokenUtil;
	@Autowired private JwtUserDetailsService jwtUserDetailsService;
	@Autowired private UserService userService;
	@Autowired private AuthenticationService authenticationService;

	@GetMapping("/teste")
	public ResponseEntity<?> teste() throws Exception {
		return null;
	}

	@PostMapping("/authenticate")
	public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {
		validateTokenRequest(authenticationRequest);

		UserApplication userApplication = authenticationService.authenticateOnLDAP(authenticationRequest.getUsername(), authenticationRequest.getPassword(), Constant.ORIGIN_SYSTEM);

		if (isNull(userApplication))
			return generateMessageInvalidCredentials();

		Authentication authentication = authenticationService.obtainIAMCredentials(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword()));

		//TODO: Set Authorities on userApplication from Credentials in authentication object

		if (isNull(authentication) || isNull(authentication.getCredentials()))
			return generateMessageInvalidCredentials();

		return ResponseEntity.ok(generateTokens(authenticationRequest));
	}

	@PostMapping("/refresh-token")
	public ResponseEntity<?> refreshToken(@RequestBody RefreshTokenRequestDTO refreshTokenRequest) throws Exception {
		if (refreshTokenRequest.getRefreshToken().isEmpty())
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);

		validateRefreshTokenRequest(refreshTokenRequest);
		if (!errorMessages.isEmpty())
			throw new ValidationException(errorMessages, new Object(){}.getClass().getEnclosingMethod().getName(), AuthenticationController.class.getName());

		String username = jwtTokenUtil.getUsernameFromRefreshToken(refreshTokenRequest.getRefreshToken());
		final UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(username);

		if (!jwtTokenUtil.validateRefreshToken(refreshTokenRequest.getRefreshToken(), userDetails))
			throw new ValidationException(errorMessages, new Object() {}.getClass().getEnclosingMethod().getName(), AuthenticationController.class.getName());

		final String token = jwtTokenUtil.generateToken(username);
		final String refreshToken = jwtTokenUtil.generateRefreshToken(username);

		return ResponseEntity.ok(new JwtDTO(token, refreshToken));
	}

	private JwtDTO generateTokens(JwtRequest authenticationRequest) {
		final String token = jwtTokenUtil.generateToken(authenticationRequest.getUsername());
		final String refreshToken = jwtTokenUtil.generateRefreshToken(authenticationRequest.getUsername());
		return new JwtDTO(token, refreshToken);
	}

	private void validateTokenRequest(JwtRequest authenticationRequest) throws ValidationException {
		errorMessages = new HashMap<>();

		if (authenticationRequest.getUsername() == null)
			errorMessages.put(MessageError.USERNAME_INVALID.name(), MessageError.USERNAME_INVALID.message);

		if (authenticationRequest.getPassword() == null) {
			errorMessages.put(MessageError.PASSWORD_INVALID.name(), MessageError.PASSWORD_INVALID.message);
		}

		if (!errorMessages.isEmpty())
			throw new ValidationException(errorMessages, new Object() {}.getClass().getEnclosingMethod().getName(), AuthenticationController.class.getName());
	}

	private ResponseEntity<?> generateMessageInvalidCredentials() {
		messages = Message.addMessage(null, Message.createMessage(MessageError.ERROR.message, MessageError.INVALID_CREDENTIALS.message, MessageError.INVALID_CREDENTIALS.name()));
		exceptionResponse = ExceptionResponse.builder().messages(messages).status(MessageError.ERROR.message).originSystem(Constant.ORIGIN_SYSTEM).locale("").time("").timezone("").build();
		return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
	}

	private void validateRefreshTokenRequest(RefreshTokenRequestDTO refreshTokenRequestDTO) {
		errorMessages = new HashMap<>();

		if (jwtTokenUtil.validateTimeRefreshToken(refreshTokenRequestDTO.getRefreshToken()))
			errorMessages.put(MessageError.AUTHENTICATION_REFRESH_TOKEN_EXPIRED.name(), MessageError.AUTHENTICATION_REFRESH_TOKEN_EXPIRED.message);
	}

}