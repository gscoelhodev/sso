package br.com.security.sso.exceptionhandling;

import java.util.List;

public class BusinessException extends ApplicationException {

	private static final long serialVersionUID = 7759264216568501358L;

	public BusinessException() {
        super();
    }

    public BusinessException(List<Message> messages, String method, String resourceClass) {
        String strMessages = messages.toString();
        setMessage(strMessages);
        setResourceMethod(method);
        setResourceClass(resourceClass);
        setException(new Exception(strMessages));
	}

}