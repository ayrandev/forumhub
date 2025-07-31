package com.alura.forumhub.controller;

import com.alura.forumhub.domain.topico.*;
import com.alura.forumhub.domain.usuario.Usuario;
import com.alura.forumhub.domain.curso.Curso;
import com.alura.forumhub.dto.DadosCadastroTopico;
import com.alura.forumhub.repository.TopicoRepository;
import com.alura.forumhub.repository.CursoRepository;
import com.alura.forumhub.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@RequestMapping("/topicos")
public class TopicoController {

    private final TopicoRepository repository;
    private final CursoRepository cursoRepository;
    private final UsuarioRepository usuarioRepository;

    public TopicoController(TopicoRepository repository,
                            UsuarioRepository usuarioRepository,
                            CursoRepository cursoRepository) {
        this.repository = repository;
        this.usuarioRepository = usuarioRepository;
        this.cursoRepository = cursoRepository;
    }

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

    @GetMapping("/{id}")
    public ResponseEntity<DadosDetalhamentoTopico> detalhar(@PathVariable @NotNull Long id) {
        Optional<Topico> topico = repository.findById(id);
        return topico.map(value -> ResponseEntity.ok(new DadosDetalhamentoTopico(value)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    @Transactional
    public ResponseEntity<DadosDetalhamentoTopico> cadastrar(@RequestBody @Valid DadosCadastroTopico dados) {
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(dados.idAutor());
        Optional<Curso> cursoOptional = cursoRepository.findById(dados.idCurso());

        if (usuarioOptional.isEmpty() || cursoOptional.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        Topico topico = new Topico(
                null,
                dados.titulo(),
                dados.mensagem(),
                LocalDateTime.now(),
                StatusTopico.NAO_RESPONDIDO,
                usuarioOptional.get(),
                cursoOptional.get()
        );

        repository.save(topico);

        return ResponseEntity.status(201).body(new DadosDetalhamentoTopico(topico));
    }

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
