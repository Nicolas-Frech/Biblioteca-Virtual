package br.com.nicolasfrech.biblioteca_online.application.user.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record UserRegistDTO(
        @NotNull
        String username,
        @NotNull
        String password,

        @NotNull
        @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")
        String email
) {
}
