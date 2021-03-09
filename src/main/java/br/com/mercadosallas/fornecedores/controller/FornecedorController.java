package br.com.mercadosallas.fornecedores.controller;

import br.com.mercadosallas.fornecedores.dto.FornecedorAtualizacaoForm;
import br.com.mercadosallas.fornecedores.dto.FornecedorDto;
import br.com.mercadosallas.fornecedores.dto.FornecedorForm;
import br.com.mercadosallas.fornecedores.service.FornecedorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/fornecedores")
public class FornecedorController {

    @Autowired
    private FornecedorService fornecedorService;

    @PostMapping
    @Transactional
    public ResponseEntity<FornecedorDto> adicionarFornecedor(@Valid @RequestBody FornecedorForm form) {
        FornecedorDto fornecedorDto = fornecedorService.adicionarFornecedor(form);
        return ResponseEntity.status(HttpStatus.CREATED).body(fornecedorDto);
    }

    @GetMapping
    public ResponseEntity<List<FornecedorDto>> listarFornecedores() {
        List<FornecedorDto> categorias = fornecedorService.listarFornecedores();
        return ResponseEntity.status(HttpStatus.OK).body(categorias);
    }

    @GetMapping("/{idCategoria}")
    public ResponseEntity<FornecedorDto> listarFornecedoresPorId(@PathVariable Long idCategoria) {
        FornecedorDto categoria = fornecedorService.buscarFornecedor(idCategoria);
        return ResponseEntity.status(HttpStatus.OK).body(categoria);
    }

    @PutMapping("/{idCategoria}")
    @Transactional
    public ResponseEntity<FornecedorDto> alterarFornecedor(@PathVariable Long idCategoria,
                                                           @RequestBody FornecedorAtualizacaoForm form) {
        FornecedorDto fornecedorDto = fornecedorService.alterarFornecedor(idCategoria, form);
        return ResponseEntity.status(HttpStatus.OK).body(fornecedorDto);
    }

    @DeleteMapping("/{idCategoria}")
    @Transactional
    public ResponseEntity<?> deletarFornecedor(@PathVariable Long idCategoria) {
        fornecedorService.deletarFornecedor(idCategoria);
        return ResponseEntity.noContent().build();
    }
}
