package br.com.nicolasfrech.biblioteca_online.infra.user.gateway;

import br.com.nicolasfrech.biblioteca_online.domain.user.User;
import br.com.nicolasfrech.biblioteca_online.infra.book.gateway.BookEntityMapper;
import br.com.nicolasfrech.biblioteca_online.infra.user.persistence.UserEntity;

import java.util.HashSet;
import java.util.stream.Collectors;

public class UserEntityMapper {

    private final BookEntityMapper bookEntityMapper;

    public UserEntityMapper(BookEntityMapper bookEntityMapper) {
        this.bookEntityMapper = bookEntityMapper;
    }

    public UserEntity toEntity(User user) {
        return new UserEntity(
                user.getId(),
                user.getUsername(),
                user.getPassword(),
                user.getMyLibrary().stream().map(bookEntityMapper::toEntity)
                                .collect(Collectors.toSet()),
                user.getEmail(),
                user.getUserRole()
        );
    }

    public User toDomain(UserEntity entity) {
        return new User(
                entity.getId(),
                entity.getUsername(),
                entity.getPassword(),
                entity.getMyLibrary().stream().map(bookEntityMapper::toDomain)
                                .collect(Collectors.toSet()),
                entity.getEmail(),
                entity.getUserRole()
        );

    }
}
