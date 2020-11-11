package br.com.security.sso.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("jwt")
@Data
public class JwtYamlProperties {
    private String secret;
    private String secretRefreshToken;
}
