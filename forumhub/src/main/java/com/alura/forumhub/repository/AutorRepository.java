package com.alura.forumhub.repository;

import com.alura.forumhub.domain.autor.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AutorRepository extends JpaRepository<Autor, Long> {
    // Não precisa adicionar nada aqui por enquanto
}
