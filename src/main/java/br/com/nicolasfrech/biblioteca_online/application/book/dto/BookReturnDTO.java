package br.com.nicolasfrech.biblioteca_online.application.book.dto;

import br.com.nicolasfrech.biblioteca_online.domain.Genre;
import br.com.nicolasfrech.biblioteca_online.domain.book.Book;

import java.time.LocalDate;

public record BookReturnDTO(Long id, String title, Genre genre, String authorName,
                            LocalDate releaseDate, String cover, String synopsis, Boolean reserved) {

    public BookReturnDTO(Book book) {
        this(book.getId(), book.getTitle(), book.getGenre(), book.getAuthor().getName(),
                book.getReleaseDate(), book.getCover(), book.getSynopsis(), book.getReserved());
    }
}
