package br.com.security.sso.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
//import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
//import javax.validation.constraints.Email;
//import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class UserDTO implements Serializable {

    private static final long serialVersionUID = 7116238547140831670L;

    private Long id;

    //@NotNull(message = "User e-mail is required")
    //@Email(message = "User e-mail should be valid")
    private String email;

    @JsonIgnore
    private String password;

}