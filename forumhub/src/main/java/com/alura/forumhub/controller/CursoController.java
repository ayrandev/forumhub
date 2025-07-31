package com.alura.forumhub.controller;

import com.alura.forumhub.domain.curso.Curso;
import com.alura.forumhub.dto.DadosCadastroCurso;
import com.alura.forumhub.repository.CursoRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cursos")
public class CursoController {

    private final CursoRepository cursoRepository;

    public CursoController(CursoRepository cursoRepository) {
        this.cursoRepository = cursoRepository;
    }

    @PostMapping
    public ResponseEntity<Curso> cadastrar(@RequestBody @Valid DadosCadastroCurso dados) {
        Curso curso = new Curso(dados.nome(), dados.categoria());
        cursoRepository.save(curso);
        return ResponseEntity.status(HttpStatus.CREATED).body(curso);
    }
}
