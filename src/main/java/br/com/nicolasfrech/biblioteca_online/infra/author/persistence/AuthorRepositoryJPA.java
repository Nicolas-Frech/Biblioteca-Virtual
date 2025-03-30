package br.com.nicolasfrech.biblioteca_online.infra.author.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AuthorRepositoryJPA extends JpaRepository<AuthorEntity, Long> {
    AuthorEntity findByName(String name);

    boolean existsByIdAndActiveTrue(Long id);

    boolean existsByNameAndActiveTrue(String name);

    @Query("SELECT a FROM AuthorEntity a LEFT JOIN FETCH a.books WHERE a.name = :name")
    AuthorEntity findByNameWithBooks(@Param("name") String name);
}
