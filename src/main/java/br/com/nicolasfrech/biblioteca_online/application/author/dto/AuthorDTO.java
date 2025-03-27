package br.com.nicolasfrech.biblioteca_online.application.author.dto;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record AuthorDTO(
        @NotNull
        String name,
        @NotNull
        LocalDate birthdate) {


}
