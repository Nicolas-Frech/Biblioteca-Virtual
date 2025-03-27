package br.com.nicolasfrech.biblioteca_online.domain.author;

import br.com.nicolasfrech.biblioteca_online.application.author.dto.AuthorDTO;
import br.com.nicolasfrech.biblioteca_online.domain.Genre;
import br.com.nicolasfrech.biblioteca_online.domain.book.Book;

import java.time.LocalDate;
import java.util.List;

public class Author {

    private Long id;
    private String name;
    private List<Genre> genres;
    private List<Book> books;
    private LocalDate birthdate;

    public Author(Long id, String name, List<Genre> genres, List<Book> books, LocalDate birthdate) {
        this.id = id;
        this.name = name;
        this.genres = genres;
        this.books = books;
        this.birthdate = birthdate;
    }

    public Author(AuthorDTO dto) {
        this.name = dto.name();
        this.birthdate = dto.birthdate();
    }

    public void addBook(Book book) {
        this.books.add(book);
    }

    public void addGenre(Genre genre) {
        this.genres.add(genre);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public List<Book> getBooks() {
        return books;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }
}
