package com.alura.forumhub.repository;

import com.alura.forumhub.domain.topico.Topico;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface TopicoRepository extends JpaRepository<Topico, Long> {
    Optional<Topico> findByTituloAndMensagem(String titulo, String mensagem);

    @Query("SELECT t FROM Topico t WHERE t.curso.nome = :nomeCurso AND YEAR(t.dataCriacao) = :ano")
    Page<Topico> findByCursoNomeAndAnoCriacao(@Param("nomeCurso") String nomeCurso, @Param("ano") Integer ano, Pageable paginacao);

    Page<Topico> findByCursoNome(String nomeCurso, Pageable paginacao);
}

