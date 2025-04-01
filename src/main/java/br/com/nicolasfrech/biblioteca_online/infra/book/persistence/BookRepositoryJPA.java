package br.com.nicolasfrech.biblioteca_online.infra.book.persistence;

import br.com.nicolasfrech.biblioteca_online.domain.Genre;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

public interface BookRepositoryJPA extends JpaRepository<BookEntity, Long> {
    boolean existsByIdAndActiveTrue(Long id);

    boolean existsByTitleAndActiveTrue(String title);

    Page<BookEntity> findAllByActiveTrue(Pageable pagination);

    @Query("SELECT b FROM BookEntity b WHERE b.title LIKE %:title% AND b.active = true")
    Page<BookEntity> findAllByTitleAndActiveTrue(String title, Pageable pagination);

    BookEntity findByTitleAndActiveTrue(String title);

    Page<BookEntity> findAllByGenreAndActiveTrue(Genre genre, Pageable pagination);
}
