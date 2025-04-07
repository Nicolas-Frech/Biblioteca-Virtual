package br.com.nicolasfrech.biblioteca_online.application.book.dto;

import jakarta.validation.constraints.NotNull;

public record BookReviewDTO(
        @NotNull
        String title,
        @NotNull
        String review) {
}
