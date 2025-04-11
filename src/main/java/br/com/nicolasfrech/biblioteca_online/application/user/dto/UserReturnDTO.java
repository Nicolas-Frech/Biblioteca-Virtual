package br.com.nicolasfrech.biblioteca_online.application.user.dto;

import br.com.nicolasfrech.biblioteca_online.domain.book.Book;
import br.com.nicolasfrech.biblioteca_online.domain.user.User;
import br.com.nicolasfrech.biblioteca_online.domain.user.UserRole;

import java.util.List;
import java.util.Set;

public record UserReturnDTO(Long id, String username, String password, Set<Book> myLibrary,
                            String email, UserRole userRole) {

    public UserReturnDTO(User user) {
        this(user.getId(), user.getUsername(), user.getPassword(), user.getMyLibrary(), user.getEmail(), user.getUserRole());
    }
}
