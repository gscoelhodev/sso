package br.com.security.sso.mapper;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import br.com.security.sso.model.User;
import br.com.security.sso.model.UserApplication;
import org.springframework.stereotype.Component;

@Component
public class UserMapper implements Serializable {

    private static final long serialVersionUID = 7684632169886863037L;

    public static User fromUserApplicationToEntity(final UserApplication userApplication) {
        return Optional.ofNullable(userApplication)
                .map(map -> User.builder()
                        .login(map.getUsername())
                        .group(map.getGroup())
                        .surName(map.getLastName())
                        .commonName(map.getFirstName())
                        .enabled(map.getEnabled())
                    .build())
                .orElse(null);
    }

    public static UserApplication fromEntityToUserApplication(final User user) {
        return Optional.ofNullable(user)
                .map(map -> UserApplication.builder()
                        .username(map.getLogin())
                        .group(map.getGroup())
                        .firstName(map.getCommonName())
                        .lastName(map.getSurName())
                        .enabled(map.getEnabled())
                    .build())
                .orElse(null);
    }

    public static List<User> fromUserApplicationToEntityList(List<UserApplication> users) {
        return users.stream().map(user -> fromUserApplicationToEntity(user)).collect(Collectors.toList());
    }

    public static List<UserApplication> fromEntityToUserApplicationList(List<User> users) {
        return users.stream().map(user -> fromEntityToUserApplication(user)).collect(Collectors.toList());
    }

}