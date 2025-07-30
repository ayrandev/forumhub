package com.alura.forumhub.domain.autor;

import jakarta.persistence.*;
import lombok.*;

@Table(name = "autores")
@Entity(name = "Autor")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Autor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String email;
}
