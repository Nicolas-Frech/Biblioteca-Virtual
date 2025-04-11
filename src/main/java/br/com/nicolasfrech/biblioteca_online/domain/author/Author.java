package br.com.nicolasfrech.biblioteca_online.domain.author;

import br.com.nicolasfrech.biblioteca_online.application.author.dto.AuthorDTO;
import br.com.nicolasfrech.biblioteca_online.domain.Genre;
import br.com.nicolasfrech.biblioteca_online.domain.book.Book;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Author {

    private Long id;
    private String name;
    private List<Book> books = new ArrayList<>();
    private LocalDate birthdate;
    private Boolean active;

    public Author() {
    }

    public Author(Long id, String name, LocalDate birthdate, Boolean active) {
        this.id = id;
        this.name = name;
        this.birthdate = birthdate;
        this.active = active;
    }

    public Author(AuthorDTO dto) {
        this.name = dto.name();
        this.birthdate = dto.birthdate();
        this.active = true;
    }

    public void deleteAuthor() {
        this.active = false;
    }

    public void addBook(Book book) {
        this.books.add(book);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Book> getBooks() {
        return books;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public Boolean getActive() {
        return active;
    }
}
