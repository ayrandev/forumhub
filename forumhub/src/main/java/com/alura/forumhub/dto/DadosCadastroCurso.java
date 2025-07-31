package com.alura.forumhub.dto;

import jakarta.validation.constraints.NotBlank;

public record DadosCadastroCurso(
        @NotBlank(message = "Nome é obrigatório")
        String nome,

        @NotBlank(message = "Categoria é obrigatória")
        String categoria
) {}
