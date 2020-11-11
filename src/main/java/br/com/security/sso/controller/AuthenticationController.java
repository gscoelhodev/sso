package br.com.security.sso.controller;

/*
import br.com.intelipost.security.iam.service.AuthorityService;
import br.com.intelipost.security.iam.service.UserService;
import br.com.intelipost.security.iam.util.HashGenarator;
import br.com.intelipost.security.iam.util.MessageSuccess;
import br.com.security.sso.dto.RefreshTokenRequestDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
*/

import br.com.security.sso.util.Constant;
import br.com.security.sso.exceptionhandling.BusinessException;
import br.com.security.sso.exceptionhandling.ResourceNotFoundException;
import br.com.security.sso.exceptionhandling.ValidationException;
import br.com.security.sso.util.MessageError;
import br.com.security.sso.exceptionhandling.ExceptionResponse;
import br.com.security.sso.exceptionhandling.Message;
import br.com.security.sso.service.JwtUserDetailsService;
import br.com.security.sso.model.JwtRequest;
import br.com.security.sso.dto.UserDTO;
import br.com.security.sso.dto.SuccessResponse;
import br.com.security.sso.dto.JwtDTO;
import br.com.security.sso.config.JwtTokenUtil;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import java.util.HashMap;
import java.util.Map;
import static java.util.Objects.isNull;
import java.util.Calendar;
import java.util.Date;

@RequestMapping(Constant.API_VERSION)
@RestController
//@Api(tags = "AUTH 0.1")
public class AuthenticationController extends ApplicationController {

	private Map<String, String> errorMessages;
	private List<Message> messages;
	private ExceptionResponse exceptionResponse;

	@Autowired private JwtTokenUtil jwtTokenUtil;
	@Autowired private JwtUserDetailsService jwtUserDetailsService;

	@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {
		validateTokenRequest(authenticationRequest);

		Authentication authentication = authenticate(authenticationRequest.getUsername(),
			authenticationRequest.getPassword());
		UserDTO user = getUserDTO(authenticationRequest.getUsername());

		final String token = jwtTokenUtil.generateToken(user);

		final String refreshToken = jwtTokenUtil.generateRefreshToken(user);

		return ResponseEntity.ok(new JwtDTO(token, refreshToken));
	}

	private Authentication authenticate(String username, String password) {
		//return userService.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		return null;
	}

	private UserDTO getUserDTO(String username) throws BusinessException {
		//return userService.findByUsername(username);
		return null;
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

	/*
	@RequestMapping(value = "/refresh-token", method = RequestMethod.POST)
	public ResponseEntity<?> refreshToken(@RequestBody RefreshTokenRequestDTO refreshTokenRequest)
		throws Exception {

		if (refreshTokenRequest.getRefreshToken().isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}

		validateRefreshTokenRequest(refreshTokenRequest);
		if (!errorMessages.isEmpty())
			throw new ValidationException(errorMessages, new Object() {
			}.getClass().getEnclosingMethod().getName(), JwtAuthenticationController.class.getName());

		String username = jwtTokenUtil
			.getUsernameFromRefreshToken(refreshTokenRequest.getRefreshToken());
		final UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(username);

		if (!jwtTokenUtil.validateRefreshToken(refreshTokenRequest.getRefreshToken(), userDetails)) {
			throw new ValidationException(errorMessages, new Object() {
			}.getClass().getEnclosingMethod().getName(), JwtAuthenticationController.class.getName());
		}

		UserDTO user = getUserDTO(username);
		CompanySecurityDTO compsecurity = companySecurityService.findByCompanyId(user.getCompanyId());

		final String token = jwtTokenUtil.generateToken(user);

		if(!authorityService.existUserKey(user))
			authorityService.saveCacheUserAuthorities(user, authorityService.getUserAuthorities(user));

		final String refreshToken = jwtTokenUtil.generateRefreshToken(user);

		if (!isNull(compsecurity) && !isNull(compsecurity.getAuthf2()) && compsecurity.getAuthf2())
			return ResponseEntity.ok(new JwtDTO(token, refreshToken, compsecurity.getAuthf2(), compsecurity.getTokenIdleTimeoutInMinutes()));

		return ResponseEntity.ok(new JwtDTO(token, refreshToken, NOT_AUTH_TWO, compsecurity.getTokenIdleTimeoutInMinutes()));

	}

	private void validateRefreshTokenRequest(RefreshTokenRequestDTO refreshTokenRequestDTO) {
		errorMessages = new HashMap<>();

		if (jwtTokenUtil.validateTimeRefreshToken(refreshTokenRequestDTO.getRefreshToken())) {
			errorMessages.put(MessageError.AUTHENTICATION_REFRESH_TOKEN_EXPIRED.name(),
				MessageError.AUTHENTICATION_REFRESH_TOKEN_EXPIRED.message);
		}

	}
	*/

}