package br.com.security.sso.config;

/*
import br.com.intelipost.security.iam.dto.Authority;
import br.com.intelipost.security.iam.dto.UserDTO;
import br.com.intelipost.security.iam.exceptionhandling.BusinessException;
import br.com.intelipost.security.iam.exceptionhandling.ValidationException;
import br.com.intelipost.security.iam.service.AuthorityService;
import br.com.intelipost.security.iam.service.UserService;
import br.com.intelipost.security.iam.util.MessageError;
*/

import br.com.security.sso.util.Constant;
import br.com.security.sso.service.JwtUserDetailsService;
import io.jsonwebtoken.ExpiredJwtException;
import java.io.IOException;
import java.util.List;
import java.util.regex.Pattern;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import static java.util.Objects.isNull;
import org.springframework.security.core.Authentication;

@Component
@Slf4j
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired private JwtUserDetailsService jwtUserDetailsService;
    @Autowired private JwtTokenUtil jwtTokenUtil;
    //@Autowired private UserService userService;
    //@Autowired private AuthorityService authorityService;
    @Autowired private CustomAccessDeniedHandler accessDeniedHandler;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        final String requestTokenHeader = request.getHeader(Constant.AUTHORIZATION);
        String username = getUserNameFromToken(requestTokenHeader);
        boolean isValid = validateToken(request, username, requestTokenHeader);

        if(!isValid){
            //accessDeniedHandler.handle(request, response, new AccessDeniedException(MessageError.AUTHENTICATION_NOT_PERMISSION.message));
            return;
        }
        chain.doFilter(request, response);
    }

    private String getUserNameFromToken(String requestTokenHeader) {
        try {
            String jwtToken = getJwtToken(requestTokenHeader);
            if(!isNull(jwtToken))
                return jwtTokenUtil.getUsernameFromToken(jwtToken);
            else {
                log.warn("JWT Token does not begin with Bearer String");
                return null;
            }
        } catch(MalformedJwtException e) {
            log.warn("Malformed Jwt Token");
            return null;
        } catch (IllegalArgumentException e) {
            log.warn("Unable to get JWT Token");
            return null;
        } catch (ExpiredJwtException e) {
            log.warn("JWT Token has expired");
            return null;
        } catch (SignatureException e) {
            log.warn("JWT signature does not match locally computed signature and should not be trusted.");
            return null;
        }
    }

    private boolean validateToken(HttpServletRequest request, String username, String requestTokenHeader) throws IOException {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        /*
        try {
            Authentication authentication = securityContext.getAuthentication();
            if (!isNull(username) && isNull(authentication)) {
                UserDetails userDetails = this.jwtUserDetailsService.loadUserByUsername(username);
                UserDTO user = userService.findByUsername(userDetails.getUsername());
                if (jwtTokenUtil.validateToken(getJwtToken(requestTokenHeader), userDetails)) {
                    if (validateAclUser(user, request)) {
                        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                            userDetails, user, userDetails.getAuthorities());
                        usernamePasswordAuthenticationToken
                            .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        securityContext.setAuthentication(usernamePasswordAuthenticationToken);

                    }else{
                        return false;
                    }
                }
            }
        } catch (Exception e){
            throw new IOException(MessageError.AUTHENTICATION_UNDEFINED.message);
        }
         */
        return true;
    }

    private String getJwtToken(String requestTokenHeader) {
        /*
        if (!isNull(requestTokenHeader) && requestTokenHeader.toLowerCase().startsWith(Constant.BEARER + Constant.WHITE_SPACE))
            return requestTokenHeader.substring(7);
        */
        return null;
    }

    /*
    private boolean validateAclUser(UserDTO user, HttpServletRequest request) throws BusinessException {

        List<Authority> authorityList =  authorityService.getCacheUserAuthorities(user);
        boolean isResourcePermission = resourcePermission(user, authorityList, request);

        if (!isResourcePermission && !user.getIsAdmin()) {
            log.warn("User not allowed to access from {} ", request.getRemoteAddr());
            return false;
        }

        return  isResourcePermission;
    }

    private boolean resourcePermission(UserDTO user, List<Authority> authorityList, HttpServletRequest request){
        log.info("method=resourcePermission from class={}, message={}", JwtRequestFilter.class.getName(), "service operation has started.");

        if(user.getIsAdmin()){
            return true;
        }

        String servletPath =  request.getServletPath();

        if (servletPath.substring(servletPath.length()-1, servletPath.length()).equals("/")){
            servletPath = servletPath.substring(0, servletPath.length() - 1);
        }

        for(Authority resource: authorityList) {
            if (request.getMethod().equals(resource.getMethod())
                && resource.getOriginSystem().equals(Constant.ORIGIN_SYSTEM)
                && Pattern.matches(resource.getPath(), servletPath)){
                return true;
            }
        }
        return false;
    }
    */

}