package br.com.nicolasfrech.biblioteca_online.application.book.dto;

import br.com.nicolasfrech.biblioteca_online.domain.Genre;

import java.time.LocalDate;

public record BookDTO(String title, Genre genre, String authorName, LocalDate releaseDate,
                      String cover, String synopsis) {
}
