package br.com.security.sso.dto;

import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
public class HeaderDTO  implements Serializable {

	private static final long serialVersionUID = -3588194479386676083L;

	public static final SignatureAlgorithm alg = SignatureAlgorithm.HS512;
	public static final String typ = "JWT";

}