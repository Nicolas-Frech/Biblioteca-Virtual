package br.com.nicolasfrech.biblioteca_online.application.user.dto;

import br.com.nicolasfrech.biblioteca_online.domain.book.Book;
import br.com.nicolasfrech.biblioteca_online.domain.user.User;
import br.com.nicolasfrech.biblioteca_online.domain.user.UserRole;

import java.util.List;

public record UserReturnDTO(Long id, String username, String password, List<Book> reservedBooks,
                            String email, UserRole userRole) {

    public UserReturnDTO(User user) {
        this(user.getId(), user.getUsername(), user.getPassword(), user.getReservedBooks(), user.getEmail(), user.getUserRole());
    }
}
