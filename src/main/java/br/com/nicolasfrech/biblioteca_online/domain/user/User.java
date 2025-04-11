package br.com.nicolasfrech.biblioteca_online.domain.user;

import br.com.nicolasfrech.biblioteca_online.domain.book.Book;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class User {

    private Long id;
    private String username;
    private String password;
    private Set<Book> myLibrary = new HashSet<>();
    private String email;
    private UserRole userRole;

    public User() {
    }

    public User(Long id, String username, String password, Set<Book> myLibrary, String email, UserRole userRole) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.myLibrary = myLibrary;
        this.email = email;
        this.userRole = userRole;
    }

    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.userRole = UserRole.USER;
    }

    public void addBookToLibrary(Book book) {
        this.myLibrary.add(book);
    }

    public void returnBook(Book book) {
        this.myLibrary.remove(book);
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

    public Set<Book> getMyLibrary() {
        return myLibrary;
    }

    public String getEmail() {
        return email;
    }

    public UserRole getUserRole() {
        return userRole;
    }


}
