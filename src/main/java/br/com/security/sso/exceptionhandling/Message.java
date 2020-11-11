package br.com.security.sso.exceptionhandling;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@Builder
public class Message {

    private String type;
    private String text;
    private String key;

    public static Message createMessage(String type, String text, String key){
        return Message.builder().type(type).text(text).key(key).build();
    }

    public static List<Message> addMessage(List<Message> messages, Message message){
        messages = Optional.ofNullable(messages).orElse(new ArrayList<Message>());
        messages.add(message);
        return messages;
    }

}