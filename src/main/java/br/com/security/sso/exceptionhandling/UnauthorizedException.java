package br.com.security.sso.exceptionhandling;

import java.util.List;
import java.util.Map;
import lombok.Getter;

@Getter
public class UnauthorizedException extends ApplicationException {

    private static final long serialVersionUID = 7759264216568501358L;
    private List<Message> messageList;

    public UnauthorizedException() {
        super();
    }

    public UnauthorizedException(Map<String, String> errorMessages, String method, String resourceClass) {
        List<Message> messages = convertMessagesWarning(errorMessages);
        this.messageList = messages;
        String strMessages = includeSeparator(messages);
        setMessage(strMessages);
        setResourceMethod(method);
        setResourceClass(resourceClass);
        setException(new Exception(strMessages));
    }

}