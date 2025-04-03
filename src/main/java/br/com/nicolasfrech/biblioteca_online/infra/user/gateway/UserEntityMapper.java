package br.com.nicolasfrech.biblioteca_online.infra.user.gateway;

import br.com.nicolasfrech.biblioteca_online.domain.user.User;
import br.com.nicolasfrech.biblioteca_online.infra.book.persistence.BookEntity;
import br.com.nicolasfrech.biblioteca_online.infra.user.persistence.UserEntity;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class UserEntityMapper {

    public UserEntity toEntity(User user) {
        return new UserEntity(
                user.getId(),
                user.getUsername(),
                user.getPassword(),
                new ArrayList<>(),
                user.getEmail(),
                user.getUserRole()
        );
    }

    public User toDomain(UserEntity entity) {
        return new User(
                entity.getId(),
                entity.getUsername(),
                entity.getPassword(),
                entity.getEmail(),
                entity.getUserRole()
        );
    }
}
