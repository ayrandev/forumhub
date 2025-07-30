package com.alura.forumhub.domain.topico;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DadosAtualizacaoTopico(
        @NotNull Long id,
        @NotBlank String titulo,
        @NotBlank String mensagem,
        @NotBlank String nomeAutor,
        @NotBlank String nomeCurso
) {
}
