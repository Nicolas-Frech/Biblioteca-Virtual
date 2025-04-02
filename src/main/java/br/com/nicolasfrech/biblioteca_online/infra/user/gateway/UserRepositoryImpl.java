package br.com.nicolasfrech.biblioteca_online.infra.user.gateway;

import br.com.nicolasfrech.biblioteca_online.application.user.gateway.UserRepository;
import br.com.nicolasfrech.biblioteca_online.domain.user.User;
import br.com.nicolasfrech.biblioteca_online.infra.user.persistence.UserEntity;
import br.com.nicolasfrech.biblioteca_online.infra.user.persistence.UserRepositoryJPA;

public class UserRepositoryImpl implements UserRepository {

    private final UserRepositoryJPA jpaRepository;

    private final UserEntityMapper mapper;

    public UserRepositoryImpl(UserRepositoryJPA jpaRepository, UserEntityMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
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
}
