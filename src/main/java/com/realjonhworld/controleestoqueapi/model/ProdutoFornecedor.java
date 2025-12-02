package com.realjonhworld.controleestoqueapi.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "produtos_fornecedores",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_produto_fornecedor",
                        columnNames = {"produto_id", "fornecedor_id"}
                )
        })
@Getter
@Setter
@NoArgsConstructor
public class ProdutoFornecedor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "produto_id")
    private Produto produto;

    @ManyToOne(optional = false)
    @JoinColumn(name = "fornecedor_id")
    private Fornecedor fornecedor;
}
