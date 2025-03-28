package br.com.nicolasfrech.biblioteca_online.infra.author.gateway;

import br.com.nicolasfrech.biblioteca_online.application.author.gateway.AuthorRepository;
import br.com.nicolasfrech.biblioteca_online.domain.author.Author;
import br.com.nicolasfrech.biblioteca_online.infra.author.persistence.AuthorEntity;
import br.com.nicolasfrech.biblioteca_online.infra.author.persistence.AuthorRepositoryJPA;

public class AuthorRepositoryImpl implements AuthorRepository {

    private final AuthorRepositoryJPA jpaRepository;

    private final AuthorEntityMapper mapper;

    public AuthorRepositoryImpl(AuthorRepositoryJPA jpaRepository, AuthorEntityMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }


    @Override
    public Author findByName(String name) {
        AuthorEntity entity = jpaRepository.findByName(name);
        return mapper.toDomain(entity);
    }

    @Override
    public Author save(Author registerAuthor) {
        AuthorEntity entity = mapper.toEntity(registerAuthor);
        jpaRepository.save(entity);
        return mapper.toDomain(entity);
    }

    @Override
    public boolean existsByIdAndActiveTrue(Long id) {
        return jpaRepository.existsByIdAndActiveTrue(id);
    }

    @Override
    public boolean existsByNameAndActiveTrue(String name) {
        return jpaRepository.existsByNameAndActiveTrue(name);
    }

    @Override
    public Author getReferenceById(Long id) {
        AuthorEntity entity = jpaRepository.getReferenceById(id);
        return mapper.toDomain(entity);
    }
}
