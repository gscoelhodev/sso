package br.com.security.sso.exceptionhandling;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import java.util.List;
import java.util.Objects;

@Data
@ToString
@Builder
public class ExceptionResponse {

    private String status;
    private String originSystem;
    private List<Message> messages;
    private String time;
    private String timezone;
    private String locale;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExceptionResponse that = (ExceptionResponse) o;
        return Objects.equals(status, that.status) &&
                Objects.equals(originSystem, that.originSystem) &&
                Objects.equals(messages, that.messages) &&
                Objects.equals(time, that.time) &&
                Objects.equals(timezone, that.timezone) &&
                Objects.equals(locale, that.locale);
    }

    @Override
    public int hashCode() {
        return Objects.hash(status, originSystem, messages, time, timezone, locale);
    }
}