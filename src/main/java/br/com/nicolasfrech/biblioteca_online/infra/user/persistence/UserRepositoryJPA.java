package br.com.nicolasfrech.biblioteca_online.infra.user.persistence;

import br.com.nicolasfrech.biblioteca_online.infra.book.persistence.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface UserRepositoryJPA extends JpaRepository<UserEntity, Long> {

    boolean existsByUsername(String username);

    UserEntity findByUsername(String username);

    boolean existsByEmail(String email);

    Set<UserEntity> findAllByMyLibraryContaining(BookEntity entity);
}
