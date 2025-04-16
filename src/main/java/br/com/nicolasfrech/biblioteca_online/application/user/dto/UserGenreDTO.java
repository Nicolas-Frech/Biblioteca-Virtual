package br.com.nicolasfrech.biblioteca_online.application.user.dto;

import br.com.nicolasfrech.biblioteca_online.infra.Genre;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record UserGenreDTO(
        @NotNull
        List<Genre> genres) {
}
