package br.com.nicolasfrech.biblioteca_online.domain.user;

import br.com.nicolasfrech.biblioteca_online.domain.book.Book;

import java.util.ArrayList;
import java.util.List;

public class User {

    private Long id;
    private String username;
    private String password;
    private List<Book> reservedBooks = new ArrayList<>();
    private String email;
    private UserRole userRole;

    public User() {
    }

    public User(Long id, String username, String password, String email, UserRole userRole) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.userRole = userRole;
    }

    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.userRole = UserRole.USER;
    }

    public void reserveBook(Book book) {
        this.reservedBooks.add(book);
    }

    public void returnBook(Book book) {
        this.reservedBooks.remove(book);
    }

    public void changeRole(UserRole role) {
        this.userRole = role;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public List<Book> getReservedBooks() {
        return reservedBooks;
    }

    public String getEmail() {
        return email;
    }

    public UserRole getUserRole() {
        return userRole;
    }


}
