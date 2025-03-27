package br.com.nicolasfrech.biblioteca_online.application.book.gateway;

import br.com.nicolasfrech.biblioteca_online.domain.book.Book;

public interface BookRepository {

    void save(Book book);

    Book getReferenceById(Long id);

    boolean existsByIdAndActiveTrue(Long id);

    boolean existsByTitleAndActiveTrue(String title);

    Book findByTitle(String title);
}
