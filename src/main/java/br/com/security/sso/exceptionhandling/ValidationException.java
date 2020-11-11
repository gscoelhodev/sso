package br.com.security.sso.exceptionhandling;

import java.util.List;
import java.util.Map;

public class ValidationException extends ApplicationException {

    private static final long serialVersionUID = 7759264216568501358L;

    public ValidationException() {
        super();
    }

    public ValidationException(Map<String, String> errorMessages, String method, String resourceClass) {
        List<Message> messages = convertMessages(errorMessages);
        String strMessages = includeSeparator(messages);
        setMessage(strMessages);
        setResourceMethod(method);
        setResourceClass(resourceClass);
        setException(new Exception(strMessages));
    }

}