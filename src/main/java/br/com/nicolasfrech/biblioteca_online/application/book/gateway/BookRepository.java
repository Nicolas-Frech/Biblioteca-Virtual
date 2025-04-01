package br.com.nicolasfrech.biblioteca_online.application.book.gateway;

import br.com.nicolasfrech.biblioteca_online.domain.book.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BookRepository {

    Book save(Book book);

    Book getReferenceById(Long id);

    boolean existsByIdAndActiveTrue(Long id);

    boolean existsByTitleAndActiveTrue(String title);

    Page<Book> findAllByActiveTrue(Pageable pagination);

    Page<Book> findAllByTitleAndActiveTrue(String title, Pageable pagination);

    Book findByTitle(String title);

    Book findByTitleAndActiveTrue(String title);
}
