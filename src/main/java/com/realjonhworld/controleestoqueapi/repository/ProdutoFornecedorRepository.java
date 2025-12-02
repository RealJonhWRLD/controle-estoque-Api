package com.realjonhworld.controleestoqueapi.repository;

import com.realjonhworld.controleestoqueapi.model.Fornecedor;
import com.realjonhworld.controleestoqueapi.model.Produto;
import com.realjonhworld.controleestoqueapi.model.ProdutoFornecedor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProdutoFornecedorRepository extends JpaRepository<ProdutoFornecedor, Long> {

    boolean existsByProdutoAndFornecedor(Produto produto, Fornecedor fornecedor);

    Optional<ProdutoFornecedor> findByProdutoAndFornecedor(Produto produto, Fornecedor fornecedor);

    List<ProdutoFornecedor> findByProduto(Produto produto);

    List<ProdutoFornecedor> findByFornecedor(Fornecedor fornecedor);
}
