package br.com.nicolasfrech.biblioteca_online.application.user.gateway;

import br.com.nicolasfrech.biblioteca_online.domain.book.Book;
import br.com.nicolasfrech.biblioteca_online.domain.user.User;

import java.util.Set;

public interface UserRepository {

    User save(User user);

    User findByUsername(String username);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    Set<User> findAllByMyLibraryContaining(Book book);
}
