package br.com.security.sso.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor(onConstructor=@__(@JsonCreator))
public class RefreshTokenRequestDTO {

    @JsonProperty("refresh_token")
    private String refreshToken;

}