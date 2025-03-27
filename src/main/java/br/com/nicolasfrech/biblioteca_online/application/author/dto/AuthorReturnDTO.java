package br.com.nicolasfrech.biblioteca_online.application.author.dto;

import br.com.nicolasfrech.biblioteca_online.domain.Genre;
import br.com.nicolasfrech.biblioteca_online.domain.author.Author;
import br.com.nicolasfrech.biblioteca_online.domain.book.Book;

import java.time.LocalDate;
import java.util.List;

public record AuthorReturnDTO(String name, List<Book> books, List<Genre> genres, LocalDate birthdate) {
    public AuthorReturnDTO(Author author) {
        this(author.getName(), author.getBooks(), author.getGenres(), author.getBirthdate());
    }
}
