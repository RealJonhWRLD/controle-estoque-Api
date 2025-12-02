package com.realjonhworld.controleestoqueapi.repository;

import com.realjonhworld.controleestoqueapi.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    boolean existsByCodigoBarras(String codigoBarras);
}
