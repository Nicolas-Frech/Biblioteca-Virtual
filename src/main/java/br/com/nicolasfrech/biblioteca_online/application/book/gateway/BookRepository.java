package br.com.nicolasfrech.biblioteca_online.application.book.gateway;

import br.com.nicolasfrech.biblioteca_online.domain.Genre;
import br.com.nicolasfrech.biblioteca_online.domain.book.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BookRepository {

    Book save(Book book);

    Book getReferenceById(Long id);

    boolean existsByIdAndActiveTrue(Long id);

    boolean existsByTitleAndActiveTrue(String title);

    Page<Book> findAllByActiveTrue(Pageable pagination);

    Page<Book> findAllByTitleAndActiveTrue(String title, Pageable pagination);

    Book findByTitle(String title);

    Book findByTitleAndActiveTrue(String title);

    Page<Book> findAllByGenreAndActiveTrue(Genre genre, Pageable pagination);

    Page<Book> findAllByAuthorAndActiveTrue(String name, Pageable pagination);

    Book findByIdAndActiveTrue(Long id);

    void saveAll(List<Book> livros);
}
