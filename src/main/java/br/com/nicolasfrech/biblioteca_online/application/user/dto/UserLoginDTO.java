package br.com.nicolasfrech.biblioteca_online.application.user.dto;

import jakarta.validation.constraints.NotNull;

public record UserLoginDTO(
        @NotNull
        String username,
        @NotNull
        String password) {
}
