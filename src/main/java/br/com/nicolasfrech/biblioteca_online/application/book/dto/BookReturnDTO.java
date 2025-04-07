package br.com.nicolasfrech.biblioteca_online.application.book.dto;

import br.com.nicolasfrech.biblioteca_online.domain.Genre;
import br.com.nicolasfrech.biblioteca_online.domain.Review;
import br.com.nicolasfrech.biblioteca_online.domain.book.Book;

import java.time.LocalDate;
import java.util.List;

public record BookReturnDTO(Long id, String title, Genre genre, String authorName,
                            LocalDate releaseDate, String cover, String synopsis, Boolean reserved, List<Review> reviews) {

    public BookReturnDTO(Book book) {
        this(book.getId(), book.getTitle(), book.getGenre(), book.getAuthor().getName(),
                book.getReleaseDate(), book.getCover(), book.getSynopsis(), book.getReserved(), book.getReviews());
    }
}
