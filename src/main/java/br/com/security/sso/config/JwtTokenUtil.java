package br.com.security.sso.config;

import br.com.security.sso.exceptionhandling.UnauthorizedException;
import br.com.security.sso.util.MessageError;
import br.com.security.sso.dto.HeaderDTO;
import br.com.security.sso.util.Constant;
import br.com.security.sso.dto.UserDTO;
import br.com.security.sso.config.properties.JwtYamlProperties;
import java.io.Serializable;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.internal.Function;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JwtTokenUtil implements Serializable {

    @Autowired private JwtYamlProperties jwtYamlProperties;

    private static final long serialVersionUID = -2550185165626007488L;
    private Map<String, Object> claims;

    public String getUsernameFromToken(String token) {
    	String userName = null;
    	try {
    		userName = getClaimFromToken(token, Claims::getSubject);
    	} catch(MalformedJwtException e) {
            log.warn("Malformed Jwt Token");
        } catch (IllegalArgumentException e) {
            log.warn("Unable to get JWT Token");
        } catch (ExpiredJwtException e) {
            log.warn("JWT Token has expired");
        } catch (SignatureException e) {
            log.warn("JWT signature does not match locally computed signature and should not be trusted.");
        }
    	return userName;
    }

    public String getUsernameFromRefreshToken(String token) {
        return getClaimFromRefreshToken(token, Claims::getSubject);
    }

    public Date getExpirationDateFromRefreshToken(String token) {
        return getClaimFromRefreshToken(token, Claims::getExpiration);
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    @SneakyThrows
    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    @SneakyThrows
    public <T> T getClaimFromRefreshToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromRefreshToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) throws UnauthorizedException {
        try {
            return Jwts.parser().setSigningKey(Constant.SECRET).parseClaimsJws(token).getBody();
        } catch (ExpiredJwtException e){
            var errorMessages = new HashMap<String, String>();
            errorMessages.put(MessageError.AUTHENTICATION_UNDEFINED.name(), MessageError.AUTHENTICATION_UNDEFINED.message);
            throw new UnauthorizedException(errorMessages, new Object(){}.getClass().getEnclosingMethod().getName(), JwtTokenUtil.class.getName());
        }
    }

    private Claims getAllClaimsFromRefreshToken(String token) throws UnauthorizedException {
    	try {
            return Jwts.parser().setSigningKey(jwtYamlProperties.getSecretRefreshToken().getBytes()).parseClaimsJws(token).getBody();
    	} catch (ExpiredJwtException e){
          var errorMessages = new HashMap<String, String>();
          errorMessages.put(MessageError.AUTHENTICATION_REFRESH_TOKEN_EXPIRED.name(), MessageError.AUTHENTICATION_REFRESH_TOKEN_EXPIRED.message);
          throw new UnauthorizedException(errorMessages, new Object(){}.getClass().getEnclosingMethod().getName(), JwtTokenUtil.class.getName());
        }
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(instantWithTimeZone());
    }

    private Boolean isRefreshTokenExpired(String token) {
        final Date expiration = getExpirationDateFromRefreshToken(token);
        return expiration.before(instantWithTimeZone());
    }

    public String generateToken(UserDTO user) {
        claims = new HashMap<>();

        return Jwts.builder().setClaims(claims)
            .setHeader(getHeader())
            .setSubject(user.getEmail())
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(expirationToken())
            .signWith(HeaderDTO.alg, Constant.SECRET)
            .compact();
    }

    public String generateRefreshToken(UserDTO user) {
        claims = new HashMap<>();
        return Jwts.builder().setClaims(claims)
            .setHeader(getHeader())
            .setSubject(user.getEmail())
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(expirationRefreshToken())
            .signWith(HeaderDTO.alg, jwtYamlProperties.getSecretRefreshToken().getBytes())
            .compact();
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    public Boolean validateRefreshToken(String refreshToken, UserDetails userDetails) {
        final String username = getUsernameFromRefreshToken(refreshToken);
        return (username.equals(userDetails.getUsername()));
    }

    public Boolean validateTimeRefreshToken(String refreshToken) {
        return isRefreshTokenExpired(refreshToken);
    }

    @SuppressWarnings("unchecked")
    private Map<String, Object> getHeader(){
        return Jwts.header().setType(HeaderDTO.typ);
    }

    private Date expirationToken(){
        ZoneId zoneId = ZoneId.of("America/Sao_Paulo");
        var expiration = new Date(System.currentTimeMillis() + Constant.EXPIRATION_TIME);
        ZonedDateTime expirationWithZone = ZonedDateTime.ofInstant(expiration.toInstant() , zoneId);
        return Date.from(expirationWithZone.toInstant());
    }

    private Date expirationRefreshToken(){
        ZoneId zoneId = ZoneId.of("America/Sao_Paulo");
        var expiration = new Date(System.currentTimeMillis() + Constant.REFRESH_TOKEN_EXPIRATION_TIME);
        ZonedDateTime expirationWithZone = ZonedDateTime.ofInstant( expiration.toInstant() , zoneId );
        return Date.from(expirationWithZone.toInstant());
    }

    private Date instantWithTimeZone() {
        ZoneId zoneId = ZoneId.of("America/Sao_Paulo");
        var currentDate = new Date(System.currentTimeMillis());
        ZonedDateTime currentDateWithZone = ZonedDateTime.ofInstant(currentDate.toInstant(), zoneId);
        return Date.from(currentDateWithZone.toInstant());
    }

}