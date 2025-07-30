package com.alura.forumhub.controller;

import com.alura.forumhub.domain.topico.*;
import com.alura.forumhub.dto.DadosCadastroTopico;
import com.alura.forumhub.repository.TopicoRepository;
import com.alura.forumhub.repository.AutorRepository;
import com.alura.forumhub.repository.CursoRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/topicos")
public class TopicoController {

    private final TopicoRepository repository;
    private final AutorRepository autorRepository;
    private final CursoRepository cursoRepository;

    public TopicoController(TopicoRepository repository,
                            AutorRepository autorRepository,
                            CursoRepository cursoRepository) {
        this.repository = repository;
        this.autorRepository = autorRepository;
        this.cursoRepository = cursoRepository;
    }

    // LISTAGEM COM FILTROS E PAGINAÇÃO
    @GetMapping
    public ResponseEntity<?> listar(
            @PageableDefault(size = 10, sort = "dataCriacao") Pageable paginacao,
            @RequestParam(required = false) String nomeCurso,
            @RequestParam(required = false) Integer ano
    ) {
        if (nomeCurso != null && ano != null) {
            var topicos = repository.findByCursoNomeAndAnoCriacao(nomeCurso, ano, paginacao);
            return ResponseEntity.ok(topicos.map(DadosListagemTopico::new));
        }

        if (nomeCurso != null) {
            var topicos = repository.findByCursoNome(nomeCurso, paginacao);
            return ResponseEntity.ok(topicos.map(DadosListagemTopico::new));
        }

        var topicos = repository.findAll(paginacao).map(DadosListagemTopico::new);
        return ResponseEntity.ok(topicos);
    }

    // DETALHAMENTO POR ID
    @GetMapping("/{id}")
    public ResponseEntity<DadosDetalhamentoTopico> detalhar(@PathVariable @NotNull Long id) {
        Optional<Topico> topico = repository.findById(id);
        if (topico.isPresent()) {
            return ResponseEntity.ok(new DadosDetalhamentoTopico(topico.get()));
        }
        return ResponseEntity.notFound().build();
    }

    // CADASTRO DE NOVO TÓPICO
    @PostMapping
    @Transactional
    public ResponseEntity<DadosDetalhamentoTopico> cadastrar(@RequestBody @Valid DadosCadastroTopico dados) {
        var autorOptional = autorRepository.findById(dados.idAutor());
        var cursoOptional = cursoRepository.findById(dados.idCurso());

        if (autorOptional.isEmpty() || cursoOptional.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        Topico topico = new Topico(
                dados.titulo(),
                dados.mensagem(),
                autorOptional.get(),
                cursoOptional.get()
        );

        repository.save(topico);

        return ResponseEntity.status(201).body(new DadosDetalhamentoTopico(topico));
    }

    // ATUALIZAÇÃO DE TÓPICO
    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<DadosDetalhamentoTopico> atualizar(@PathVariable Long id,
                                                             @RequestBody @Valid DadosAtualizacaoTopico dados) {
        Optional<Topico> optionalTopico = repository.findById(id);

        if (optionalTopico.isPresent()) {
            Topico topico = optionalTopico.get();
            topico.setTitulo(dados.titulo());
            topico.setMensagem(dados.mensagem());

            return ResponseEntity.ok(new DadosDetalhamentoTopico(topico));
        }

        return ResponseEntity.notFound().build();
    }

    // EXCLUSÃO DE TÓPICO
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        Optional<Topico> topicoOptional = repository.findById(id);

        if (topicoOptional.isPresent()) {
            repository.deleteById(id);
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();
    }

}
