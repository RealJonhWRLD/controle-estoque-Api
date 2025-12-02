package com.realjonhworld.controleestoqueapi.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "produtos",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_produto_codigobarras",
                        columnNames = "codigoBarras"
                )
        })
@Getter
@Setter
@NoArgsConstructor
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String nome;

    @NotBlank
    @Column(nullable = false)
    private String codigoBarras;

    @NotBlank
    private String descricao;

    @NotNull
    private Integer quantidadeEstoque;

    @NotBlank
    private String categoria;

    private LocalDate dataValidade;

    private String imagemUrl; // opcional, para guardar caminho da imagem

    @NotNull
    private Double preco;
}
