package br.com.security.sso.exceptionhandling;

import java.util.List;

public class ResourceNotFoundException extends ApplicationException {

	private static final long serialVersionUID = 2431563568101384085L;

	public ResourceNotFoundException() {
        super();
    }

    public ResourceNotFoundException(List<Message> messages, String method, String resourceClass) {
        String strMessages = includeSeparator(messages);
        setMessage(strMessages);
        setResourceMethod(method);
        setResourceClass(resourceClass);
        setException(new Exception(strMessages));
    }

}
