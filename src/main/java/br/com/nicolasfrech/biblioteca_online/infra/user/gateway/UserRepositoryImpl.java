package br.com.nicolasfrech.biblioteca_online.infra.user.gateway;

import br.com.nicolasfrech.biblioteca_online.application.user.gateway.UserRepository;
import br.com.nicolasfrech.biblioteca_online.domain.book.Book;
import br.com.nicolasfrech.biblioteca_online.domain.user.User;
import br.com.nicolasfrech.biblioteca_online.infra.book.gateway.BookEntityMapper;
import br.com.nicolasfrech.biblioteca_online.infra.book.persistence.BookEntity;
import br.com.nicolasfrech.biblioteca_online.infra.user.persistence.UserEntity;
import br.com.nicolasfrech.biblioteca_online.infra.user.persistence.UserRepositoryJPA;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class UserRepositoryImpl implements UserRepository {

    private final UserRepositoryJPA jpaRepository;

    private final UserEntityMapper mapper;

    private final BookEntityMapper bookEntityMapper;

    public UserRepositoryImpl(UserRepositoryJPA jpaRepository, UserEntityMapper mapper, BookEntityMapper bookEntityMapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
        this.bookEntityMapper = bookEntityMapper;
    }


    @Override
    public User save(User user) {
        UserEntity entity = mapper.toEntity(user);
        jpaRepository.save(entity);
        return mapper.toDomain(entity);
    }

    @Override
    public User findByUsername(String username) {
        return mapper.toDomain(jpaRepository.findByUsername(username));
    }

    @Override
    public boolean existsByUsername(String username) {
        return jpaRepository.existsByUsername(username);
    }

    @Override
    public boolean existsByEmail(String email) {
        return jpaRepository.existsByEmail(email);
    }

    @Override
    public Set<User> findAllByMyLibraryContaining(Book book) {
        BookEntity entity = bookEntityMapper.toEntity(book);
        Set<User> users = jpaRepository.findAllByMyLibraryContaining(entity).stream()
                .map(mapper::toDomain).collect(Collectors.toSet());

        return users;
    }
}
