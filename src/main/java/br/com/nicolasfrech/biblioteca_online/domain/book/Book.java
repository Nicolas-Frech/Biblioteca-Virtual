package br.com.nicolasfrech.biblioteca_online.domain.book;

import br.com.nicolasfrech.biblioteca_online.application.book.dto.BookDTO;
import br.com.nicolasfrech.biblioteca_online.domain.Genre;
import br.com.nicolasfrech.biblioteca_online.domain.author.Author;

import java.time.LocalDate;

public class Book {

    private Long id;
    private String title;
    private Genre genre;
    private Author author;
    private String cover;
    private LocalDate releaseDate;
    private String synopsis;
    private Boolean reserved;
    private Boolean active;

    public Long getId() {
        return id;
    }

    public Book(Long id, String title, Genre genre, String cover, LocalDate releaseDate, String synopsis) {
        this.id = id;
        this.title = title;
        this.genre = genre;
        this.cover = cover;
        this.releaseDate = releaseDate;
        this.synopsis = synopsis;
        this.reserved = false;
        this.active = true;
    }

    public Book(BookDTO dto) {
        this.title = dto.title();
        this.genre = dto.genre();
        this.cover = dto.cover();
        this.releaseDate = dto.releaseDate();
        this.synopsis = dto.synopsis();
    }

    public void addAuthor(Author author) {
        this.author = author;
    }

    public void deleteBook() {
        this.active = false;
    }

    public void reserveBook() {
        this.reserved = true;
    }

    public String getTitle() {
        return title;
    }

    public Genre getGenre() {
        return genre;
    }

    public Author getAuthor() {
        return author;
    }

    public String getCover() {
        return cover;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public Boolean getReserved() {
        return reserved;
    }

    public Boolean getActive() {
        return active;
    }
}
