package br.com.nicolasfrech.biblioteca_online.application.user.dto;

import br.com.nicolasfrech.biblioteca_online.domain.user.UserRole;
import jakarta.validation.constraints.NotNull;

public record UserUpdateDTO(
        @NotNull
        String username,
        @NotNull
        UserRole role) {
}
