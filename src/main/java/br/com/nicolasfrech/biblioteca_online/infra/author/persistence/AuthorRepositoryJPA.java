package br.com.nicolasfrech.biblioteca_online.infra.author.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepositoryJPA extends JpaRepository<AuthorEntity, Long> {
    AuthorEntity findByName(String name);

    boolean existsByIdAndActiveTrue(Long id);

    boolean existsByNameAndActiveTrue(String name);
}
