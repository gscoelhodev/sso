package br.com.security.sso.dto;

import br.com.security.sso.exceptionhandling.Message;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import org.springframework.http.HttpStatus;
import java.util.List;
import java.util.Objects;

@Data
@ToString
@Builder
public class SuccessResponse {

    private String status;
    private List<Message> messages;
    private String time;
    private String timezone;
    private String locale;

    public String getStatus() {
        return HttpStatus.OK.name();
    }

    public String getTime() {
        return "";
    }

    public String getTimezone() {
        return "";
    }

    public String getLocale() {
        return "";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SuccessResponse that = (SuccessResponse) o;
        return Objects.equals(status, that.status) &&
                Objects.equals(messages, that.messages) &&
                Objects.equals(time, that.time) &&
                Objects.equals(timezone, that.timezone) &&
                Objects.equals(locale, that.locale);
    }

    @Override
    public int hashCode() {
        return Objects.hash(status, messages, time, timezone, locale);
    }

}