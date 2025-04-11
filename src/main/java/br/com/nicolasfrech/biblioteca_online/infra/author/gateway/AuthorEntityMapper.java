package br.com.nicolasfrech.biblioteca_online.infra.author.gateway;

import br.com.nicolasfrech.biblioteca_online.domain.author.Author;
import br.com.nicolasfrech.biblioteca_online.infra.author.persistence.AuthorEntity;

import java.util.ArrayList;

public class AuthorEntityMapper {

    public AuthorEntity toEntity(Author author) {
        return new AuthorEntity(
                author.getId(),
                author.getName(),
                new ArrayList<>(),
                author.getBirthdate(),
                author.getActive()
        );
    }

    public Author toDomain(AuthorEntity entity) {
        return new Author(
                entity.getId(),
                entity.getName(),
                entity.getBirthdate(),
                entity.getActive()
        );
    }
}
