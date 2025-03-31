package br.com.nicolasfrech.biblioteca_online.infra.book.persistence;

import br.com.nicolasfrech.biblioteca_online.domain.book.Book;
import br.com.nicolasfrech.biblioteca_online.infra.book.gateway.BookRepositoryImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Pageable;

public interface BookRepositoryJPA extends JpaRepository<BookEntity, Long> {
    boolean existsByIdAndActiveTrue(Long id);

    boolean existsByTitleAndActiveTrue(String title);

    BookEntity findByTitle(String title);

    Page<BookEntity> findAllByActiveTrue(Pageable pagination);

    Page<BookEntity> findAllByTitleAndActiveTrue(String title, Pageable pagination);

    BookEntity findByTitleAndActiveTrue(String title);
}
