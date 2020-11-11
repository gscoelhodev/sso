package br.com.security.sso.exceptionhandling;

import br.com.security.sso.util.Constant;
import br.com.security.sso.util.MessageError;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Data
@EqualsAndHashCode(callSuper=false)
@ToString
public class ApplicationException extends Exception {

	private static final long serialVersionUID = 1723522633720975846L;

	private String message;
    private String resourceMethod;
    private String resourceClass;
    private Exception exception;
    private String strException;

    public ApplicationException() {
        super();
    }

    public ApplicationException(final String message, String method, String resourceClass) {
    	super(new Exception(new StringBuilder().append(message).append(resourceClass).toString()));
    	this.message = message;
    	this.resourceMethod = method;
    	this.resourceClass = resourceClass;
        this.strException = new StringBuilder().append(message).append(" on method ").append(method).append(" from class={} ").append(resourceClass).toString();
    	this.exception = new Exception(strException);
    }

    public ApplicationException(final String message, String method, String resourceClass, Exception exception) {
        super(exception.getMessage(), exception.getCause());
    	this.message = message;
    	this.resourceMethod = method;
    	this.resourceClass = resourceClass;
    	this.exception = exception;
    }

    public String includeSeparator(List<Message> messages){
        StringBuilder strMessages = new StringBuilder();
        for (Message message : messages) {
            strMessages.append(message).append(Constant.SEMICOLON_SEPARATOR);
        }
        return strMessages.toString();
    }

    public List<Message> convertMessages(Map<String, String> errorMessages){
        List<Message> messages = new ArrayList<>();
        Iterator<Map.Entry<String, String>> itr = errorMessages.entrySet().iterator();
        while(itr.hasNext()){
            Map.Entry<String, String> entry = itr.next();
            messages.add(Message.createMessage(MessageError.ERROR.message, entry.getValue(), entry.getKey()));
        }
        return messages;
    }


    public List<Message> convertMessagesWarning(Map<String, String> errorMessages){
        List<Message> messages = new ArrayList<>();
        Iterator<Map.Entry<String, String>> itr = errorMessages.entrySet().iterator();
        while(itr.hasNext()){
            Map.Entry<String, String> entry = itr.next();
            messages.add(Message.createMessage(MessageError.WARNING.message, entry.getValue(), entry.getKey()));
        }
        return messages;
    }

}