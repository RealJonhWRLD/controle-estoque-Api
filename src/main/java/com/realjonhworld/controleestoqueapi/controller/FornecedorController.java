package com.realjonhworld.controleestoqueapi.controller;

import com.realjonhworld.controleestoqueapi.model.Fornecedor;
import com.realjonhworld.controleestoqueapi.repository.FornecedorRepository;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/fornecedores")
@CrossOrigin(origins = "*")
public class FornecedorController {

    private final FornecedorRepository fornecedorRepository;

    public FornecedorController(FornecedorRepository fornecedorRepository) {
        this.fornecedorRepository = fornecedorRepository;
    }

    @GetMapping
    public List<Fornecedor> listar() {
        return fornecedorRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Fornecedor> buscarPorId(@PathVariable Long id) {
        return fornecedorRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> criar(@Valid @RequestBody Fornecedor fornecedor) {
        if (fornecedorRepository.existsByCnpj(fornecedor.getCnpj())) {
            return ResponseEntity.badRequest()
                    .body("Fornecedor com esse CNPJ já está cadastrado!");
        }
        Fornecedor salvo = fornecedorRepository.save(fornecedor);
        return ResponseEntity.created(URI.create("/fornecedores/" + salvo.getId()))
                .body(salvo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Long id,
                                       @Valid @RequestBody Fornecedor fornecedorAtualizado) {
        return fornecedorRepository.findById(id)
                .map(fornecedor -> {
                    fornecedor.setNomeEmpresa(fornecedorAtualizado.getNomeEmpresa());
                    fornecedor.setCnpj(fornecedorAtualizado.getCnpj());
                    fornecedor.setEndereco(fornecedorAtualizado.getEndereco());
                    fornecedor.setTelefone(fornecedorAtualizado.getTelefone());
                    fornecedor.setEmail(fornecedorAtualizado.getEmail());
                    fornecedor.setContatoPrincipal(fornecedorAtualizado.getContatoPrincipal());
                    fornecedorRepository.save(fornecedor);
                    return ResponseEntity.ok(fornecedor);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (!fornecedorRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        fornecedorRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
