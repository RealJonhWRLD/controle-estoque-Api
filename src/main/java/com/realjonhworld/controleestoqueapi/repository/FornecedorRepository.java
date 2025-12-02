package com.realjonhworld.controleestoqueapi.repository;

import com.realjonhworld.controleestoqueapi.model.Fornecedor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FornecedorRepository extends JpaRepository<Fornecedor, Long> {

    boolean existsByCnpj(String cnpj);
}
