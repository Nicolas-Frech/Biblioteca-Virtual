package br.com.nicolasfrech.biblioteca_online.application.book.dto;

import jakarta.validation.constraints.NotNull;

public record BookRateDTO (
        @NotNull
        String title,
        @NotNull
        Integer rating) {
}
