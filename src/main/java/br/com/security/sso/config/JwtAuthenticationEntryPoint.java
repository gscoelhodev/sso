package br.com.security.sso.config;

//import br.com.intelipost.security.iam.util.Constant;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
//import br.com.intelipost.security.iam.util.MessageError;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint, Serializable {

    private static final long serialVersionUID = -7858869558953243875L;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse res, AuthenticationException authException) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        res.setContentType("application/json;charset=UTF-8");
        res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        var response = new HashMap<String, Object>();
        var messages = new HashMap<String, Object>();
        response.put("time", System.currentTimeMillis()+" ms");
        //response.put("originSystem", Constant.ORIGIN_SYSTEM);
        response.put("timezone", "");
        response.put("locale", "");

        messages.put("type", "WARNING");
        //messages.put("text", MessageError.AUTHENTICATION_UNDEFINED.message);
        //messages.put("key", MessageError.AUTHENTICATION_UNDEFINED.name());
        List<HashMap<String, Object>> messagesList = new ArrayList<>();
        messagesList.add(messages);
        response.put("messages", messagesList);
        response.put("status", "401");
        res.getWriter().write(mapper.writeValueAsString(response));
    }

}