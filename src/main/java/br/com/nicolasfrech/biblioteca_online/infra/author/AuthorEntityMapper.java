package br.com.nicolasfrech.biblioteca_online.infra.author;

import br.com.nicolasfrech.biblioteca_online.domain.author.Author;

public class AuthorEntityMapper {

    public AuthorEntity toEntity(Author author) {
        return new AuthorEntity(
                author.getId(),
                author.getName(),
                author.getGenres(),
                null,
                author.getBirthdate(),
                author.getActive()
        );
    }

    public Author toDomain(AuthorEntity entity) {
        return new Author(
                entity.getId(),
                entity.getName(),
                entity.getGenres(),
                entity.getBirthdate(),
                entity.getActive()
        );
    }
}
