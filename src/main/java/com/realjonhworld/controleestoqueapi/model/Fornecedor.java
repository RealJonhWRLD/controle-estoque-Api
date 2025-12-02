package com.realjonhworld.controleestoqueapi.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "fornecedores",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_fornecedor_cnpj",
                        columnNames = "cnpj"
                )
        })
@Getter
@Setter
@NoArgsConstructor
public class Fornecedor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String nomeEmpresa;

    @NotBlank
    @Column(nullable = false, length = 18)
    private String cnpj;

    @NotBlank
    private String endereco;

    @NotBlank
    private String telefone;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String contatoPrincipal;
}
