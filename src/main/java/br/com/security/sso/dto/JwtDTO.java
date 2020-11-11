package br.com.security.sso.dto;

import br.com.security.sso.util.Constant;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class JwtDTO {
	
	@JsonProperty("access_token")
    @NonNull
    private String accessToken;

    @JsonProperty("refresh_token")
    @NonNull
    private String refreshToken;

    @JsonProperty("expires_in")
    private Long expiresIn = Constant.EXPIRATION_TIME;

    @JsonProperty("type_token")
    private String typeToken = Constant.BEARER;

}