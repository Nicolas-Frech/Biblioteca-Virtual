package br.com.nicolasfrech.biblioteca_online.application.book.gateway;

import br.com.nicolasfrech.biblioteca_online.domain.book.Book;

public interface BookRepository {

    void save(Book book);

    Book getReferenceById(Long id);

    boolean existsById(Long id);

    boolean existsByTitle(String title);
}
