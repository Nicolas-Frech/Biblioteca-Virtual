package br.com.nicolasfrech.biblioteca_online.application.author.dto;

import br.com.nicolasfrech.biblioteca_online.domain.author.Author;
import br.com.nicolasfrech.biblioteca_online.domain.book.Book;

import java.time.LocalDate;
import java.util.List;

public record AuthorReturnDTO(Long id, String name, List<Book> books,  LocalDate birthdate) {
    public AuthorReturnDTO(Author author) {
        this(author.getId(), author.getName(), author.getBooks(), author.getBirthdate());
    }
}
