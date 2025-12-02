package com.realjonhworld.controleestoqueapi.controller;

import com.realjonhworld.controleestoqueapi.model.Produto;
import com.realjonhworld.controleestoqueapi.repository.ProdutoRepository;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/produtos")
@CrossOrigin(origins = "*")
public class ProdutoController {

    private final ProdutoRepository produtoRepository;

    public ProdutoController(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    @GetMapping
    public List<Produto> listar() {
        return produtoRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Produto> buscarPorId(@PathVariable Long id) {
        return produtoRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> criar(@Valid @RequestBody Produto produto) {
        if (produtoRepository.existsByCodigoBarras(produto.getCodigoBarras())) {
            return ResponseEntity.badRequest()
                    .body("Produto com este código de barras já está cadastrado!");
        }
        Produto salvo = produtoRepository.save(produto);
        return ResponseEntity.created(URI.create("/produtos/" + salvo.getId()))
                .body(salvo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Long id,
                                       @Valid @RequestBody Produto produtoAtualizado) {
        return produtoRepository.findById(id)
                .map(produto -> {
                    produto.setNome(produtoAtualizado.getNome());
                    produto.setCodigoBarras(produtoAtualizado.getCodigoBarras());
                    produto.setDescricao(produtoAtualizado.getDescricao());
                    produto.setQuantidadeEstoque(produtoAtualizado.getQuantidadeEstoque());
                    produto.setCategoria(produtoAtualizado.getCategoria());
                    produto.setDataValidade(produtoAtualizado.getDataValidade());
                    produto.setImagemUrl(produtoAtualizado.getImagemUrl());
                    produto.setPreco(produtoAtualizado.getPreco());
                    produtoRepository.save(produto);
                    return ResponseEntity.ok(produto);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (!produtoRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        produtoRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
