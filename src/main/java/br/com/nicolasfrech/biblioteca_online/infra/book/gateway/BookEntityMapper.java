package br.com.nicolasfrech.biblioteca_online.infra.book.gateway;


import br.com.nicolasfrech.biblioteca_online.domain.book.Book;
import br.com.nicolasfrech.biblioteca_online.infra.author.gateway.AuthorEntityMapper;
import br.com.nicolasfrech.biblioteca_online.infra.book.persistence.BookEntity;
import br.com.nicolasfrech.biblioteca_online.infra.user.gateway.UserEntityMapper;

public class BookEntityMapper {

    private final AuthorEntityMapper authorMapper;

    private final UserEntityMapper userMapper;

    public BookEntityMapper(AuthorEntityMapper authorMapper, UserEntityMapper userMapper) {
        this.authorMapper = authorMapper;
        this.userMapper = userMapper;
    }


    public BookEntity toEntity(Book book) {
        BookEntity entity = new BookEntity(
                book.getId(),
                book.getTitle(),
                book.getGenre(),
                authorMapper.toEntity(book.getAuthor()),
                book.getUserReserved() != null && book.getUserReserved().getId() != null
                        ? userMapper.toEntity(book.getUserReserved())
                        : null,
                book.getCover(), book.getReleaseDate(),
                book.getSynopsis(),
                book.getReserved(),
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
                entity.getUserReserved() != null ? userMapper.toDomain(entity.getUserReserved()) : null,
                entity.getCover(),
                entity.getReleaseDate(),
                entity.getSynopsis(),
                entity.getReserved(),
                entity.getActive(),
                entity.getReviews()
        );

        book.setRatings(entity.getRatings());
        return book;
    }
}
