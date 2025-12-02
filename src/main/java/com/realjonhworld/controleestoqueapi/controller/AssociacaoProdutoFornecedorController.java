package com.realjonhworld.controleestoqueapi.controller;

import com.realjonhworld.controleestoqueapi.model.Fornecedor;
import com.realjonhworld.controleestoqueapi.model.Produto;
import com.realjonhworld.controleestoqueapi.model.ProdutoFornecedor;
import com.realjonhworld.controleestoqueapi.repository.FornecedorRepository;
import com.realjonhworld.controleestoqueapi.repository.ProdutoFornecedorRepository;
import com.realjonhworld.controleestoqueapi.repository.ProdutoRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/associacoes")
@CrossOrigin(origins = "*")
public class AssociacaoProdutoFornecedorController {

    private final ProdutoRepository produtoRepository;
    private final FornecedorRepository fornecedorRepository;
    private final ProdutoFornecedorRepository produtoFornecedorRepository;

    public AssociacaoProdutoFornecedorController(ProdutoRepository produtoRepository,
                                                 FornecedorRepository fornecedorRepository,
                                                 ProdutoFornecedorRepository produtoFornecedorRepository) {
        this.produtoRepository = produtoRepository;
        this.fornecedorRepository = fornecedorRepository;
        this.produtoFornecedorRepository = produtoFornecedorRepository;
    }

    @PostMapping
    public ResponseEntity<?> associar(@RequestParam Long produtoId,
                                      @RequestParam Long fornecedorId) {

        Produto produto = produtoRepository.findById(produtoId).orElse(null);
        Fornecedor fornecedor = fornecedorRepository.findById(fornecedorId).orElse(null);

        if (produto == null || fornecedor == null) {
            return ResponseEntity.badRequest().body("Produto ou fornecedor inexistente.");
        }

        if (produtoFornecedorRepository.existsByProdutoAndFornecedor(produto, fornecedor)) {
            return ResponseEntity.badRequest()
                    .body("Fornecedor já está associado a este produto!");
        }

        ProdutoFornecedor assoc = new ProdutoFornecedor();
        assoc.setProduto(produto);
        assoc.setFornecedor(fornecedor);

        produtoFornecedorRepository.save(assoc);
        return ResponseEntity.ok("Fornecedor associado com sucesso ao produto!");
    }

    @DeleteMapping
    public ResponseEntity<?> desassociar(@RequestParam Long produtoId,
                                         @RequestParam Long fornecedorId) {

        Produto produto = produtoRepository.findById(produtoId).orElse(null);
        Fornecedor fornecedor = fornecedorRepository.findById(fornecedorId).orElse(null);

        if (produto == null || fornecedor == null) {
            return ResponseEntity.badRequest().body("Produto ou fornecedor inexistente.");
        }

        return produtoFornecedorRepository.findByProdutoAndFornecedor(produto, fornecedor)
                .map(assoc -> {
                    produtoFornecedorRepository.delete(assoc);
                    return ResponseEntity.ok("Fornecedor desassociado com sucesso!");
                })
                .orElse(ResponseEntity.badRequest()
                        .body("Associação não encontrada."));
    }

    @GetMapping("/produto/{produtoId}/fornecedores")
    public ResponseEntity<?> listarFornecedoresDoProduto(@PathVariable Long produtoId) {
        Produto produto = produtoRepository.findById(produtoId).orElse(null);
        if (produto == null) {
            return ResponseEntity.notFound().build();
        }
        List<ProdutoFornecedor> associacoes = produtoFornecedorRepository.findByProduto(produto);
        return ResponseEntity.ok(associacoes);
    }

    @GetMapping("/fornecedor/{fornecedorId}/produtos")
    public ResponseEntity<?> listarProdutosDoFornecedor(@PathVariable Long fornecedorId) {
        Fornecedor fornecedor = fornecedorRepository.findById(fornecedorId).orElse(null);
        if (fornecedor == null) {
            return ResponseEntity.notFound().build();
        }
        List<ProdutoFornecedor> associacoes = produtoFornecedorRepository.findByFornecedor(fornecedor);
        return ResponseEntity.ok(associacoes);
    }
}
