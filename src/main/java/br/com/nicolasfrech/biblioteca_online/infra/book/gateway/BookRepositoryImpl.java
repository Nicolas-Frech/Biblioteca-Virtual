package br.com.nicolasfrech.biblioteca_online.infra.book.gateway;

import br.com.nicolasfrech.biblioteca_online.application.book.gateway.BookRepository;
import br.com.nicolasfrech.biblioteca_online.domain.book.Book;
import br.com.nicolasfrech.biblioteca_online.infra.book.persistence.BookEntity;
import br.com.nicolasfrech.biblioteca_online.infra.book.persistence.BookRepositoryJPA;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public class BookRepositoryImpl implements BookRepository {

    private final BookRepositoryJPA jpaRepository;

    private final BookEntityMapper mapper;

    public BookRepositoryImpl(BookRepositoryJPA jpaRepository, BookEntityMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public Book save(Book book) {
        BookEntity bookEntity = mapper.toEntity(book);
        jpaRepository.save(bookEntity);

        return mapper.toDomain(bookEntity);
    }

    @Override
    public Book getReferenceById(Long id) {
        BookEntity bookEntity = jpaRepository.getReferenceById(id);
        return mapper.toDomain(bookEntity);
    }

    @Override
    public boolean existsByIdAndActiveTrue(Long id) {
        return jpaRepository.existsByIdAndActiveTrue(id);
    }

    @Override
    public boolean existsByTitleAndActiveTrue(String title) {
        return jpaRepository.existsByTitleAndActiveTrue(title);
    }

    @Override
    public Page<Book> findAllByActiveTrue(Pageable pagination) {
        Page<Book> books = jpaRepository.findAllByActiveTrue(pagination).map(mapper::toDomain);
        return books;
    }

    @Override
    public Page<Book> findAllByTitleAndActiveTrue(String title, Pageable pagination) {
        Page<Book> books = jpaRepository.findAllByTitleAndActiveTrue(title, pagination)
                .map(mapper::toDomain);
        return books;
    }

    @Override
    public Book findByTitle(String title) {
        return mapper.toDomain(jpaRepository.findByTitleAndActiveTrue(title));
    }

    @Override
    public Book findByTitleAndActiveTrue(String title) {
        return mapper.toDomain(jpaRepository.findByTitleAndActiveTrue(title));
    }
}
