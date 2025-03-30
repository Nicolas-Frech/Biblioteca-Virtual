package br.com.nicolasfrech.biblioteca_online.infra.author.gateway;

import br.com.nicolasfrech.biblioteca_online.application.author.gateway.AuthorRepository;
import br.com.nicolasfrech.biblioteca_online.domain.author.Author;
import br.com.nicolasfrech.biblioteca_online.infra.author.persistence.AuthorEntity;
import br.com.nicolasfrech.biblioteca_online.infra.author.persistence.AuthorRepositoryJPA;
import br.com.nicolasfrech.biblioteca_online.infra.book.gateway.BookEntityMapper;

public class AuthorRepositoryImpl implements AuthorRepository {

    private final AuthorRepositoryJPA jpaRepository;

    private final AuthorEntityMapper mapper;

    private final BookEntityMapper bookEntityMapper;

    public AuthorRepositoryImpl(AuthorRepositoryJPA jpaRepository, AuthorEntityMapper mapper, BookEntityMapper bookEntityMapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
        this.bookEntityMapper = bookEntityMapper;
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

    @Override
    public Author findByNameWithBooks(String name) {
        AuthorEntity entity = jpaRepository.findByNameWithBooks(name);
        Author author = mapper.toDomain(entity);
        entity.getBooks().forEach(b -> author.addBook(bookEntityMapper.toDomain(b)));
        return author;
    }
}
