package br.com.security.sso.config;

//import br.com.intelipost.security.iam.util.Constant;
//import br.com.intelipost.security.iam.util.MessageError;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest req, HttpServletResponse res, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        /*
        ObjectMapper mapper = new ObjectMapper();
        res.setContentType("application/json;charset=UTF-8");
        res.setStatus(403);
        var response = new HashMap<String, Object>();
        var messages = new HashMap<String, Object>();
        response.put("time", System.currentTimeMillis()+" ms");
        response.put("timezone", "");
        response.put("locale", "");

        messages.put("type", "403");
        response.put("originSystem", Constant.ORIGIN_SYSTEM);
        messages.put("text", accessDeniedException.getMessage());
        messages.put("key", MessageError.AUTHENTICATION_NOT_PERMISSION.name());
        List<HashMap<String, Object>> messagesList = new ArrayList<>();
        messagesList.add(messages);
        response.put("messages", messagesList);
        response.put("status", "WARNING");
        res.getWriter().write(mapper.writeValueAsString(response));
        */
    }

}
