package br.com.nicolasfrech.biblioteca_online.infra.book.gateway;


import br.com.nicolasfrech.biblioteca_online.domain.book.Book;
import br.com.nicolasfrech.biblioteca_online.infra.author.AuthorEntityMapper;
import br.com.nicolasfrech.biblioteca_online.infra.book.persistence.BookEntity;

public class BookEntityMapper {

    private final AuthorEntityMapper authorMapper;

    public BookEntityMapper(AuthorEntityMapper authorMapper) {
        this.authorMapper = authorMapper;
    }


    public BookEntity toEntity(Book book) {
        return new BookEntity(
                book.getId(),
                book.getTitle(),
                book.getGenre(),
                authorMapper.toEntity(book.getAuthor()),
                book.getCover(), book.getReleaseDate(),
                book.getSynopsis(),
                book.getReserved(),
                book.getActive()
        );
    }

    public Book toDomain(BookEntity entity) {
        return new Book(
                entity.getId(),
                entity.getTitle(),
                entity.getGenre(),
                authorMapper.toDomain(entity.getAuthor()),
                entity.getCover(),
                entity.getReleaseDate(),
                entity.getSynopsis(),
                entity.getReserved(),
                entity.getActive());
    }
}
