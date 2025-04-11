package br.com.nicolasfrech.biblioteca_online.infra.book.gateway;


import br.com.nicolasfrech.biblioteca_online.domain.book.Book;
import br.com.nicolasfrech.biblioteca_online.infra.author.gateway.AuthorEntityMapper;
import br.com.nicolasfrech.biblioteca_online.infra.book.persistence.BookEntity;

import java.util.HashSet;

public class BookEntityMapper {

    private final AuthorEntityMapper authorMapper;

    public BookEntityMapper(AuthorEntityMapper authorMapper) {
        this.authorMapper = authorMapper;
    }


    public BookEntity toEntity(Book book) {
        BookEntity entity = new BookEntity(
                book.getId(),
                book.getTitle(),
                book.getGenre(),
                authorMapper.toEntity(book.getAuthor()),
                new HashSet<>(),
                book.getCover(), book.getReleaseDate(),
                book.getSynopsis(),
                book.getActive(),
                null
        );
        entity.setReviews(book.getReviews());
        entity.setRatings(book.getRatings());
        return entity;
    }

    public Book toDomain(BookEntity entity) {
         Book book = new Book(
                entity.getId(),
                entity.getTitle(),
                entity.getGenre(),
                authorMapper.toDomain(entity.getAuthor()),
                new HashSet<>(),
                entity.getCover(),
                entity.getReleaseDate(),
                entity.getSynopsis(),
                entity.getActive(),
                entity.getReviews()
        );
        book.setRatings(entity.getRatings());
        return book;
    }
}
