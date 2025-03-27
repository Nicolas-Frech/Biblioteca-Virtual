package br.com.nicolasfrech.biblioteca_online.application.book.dto;

import br.com.nicolasfrech.biblioteca_online.domain.Genre;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record BookDTO(
        @NotNull
        String title,
        @NotNull
        Genre genre,
        @NotNull
        String authorName,
        @NotNull
        LocalDate releaseDate,
        @NotNull
        String cover,
        @NotNull
        String synopsis) {
}
