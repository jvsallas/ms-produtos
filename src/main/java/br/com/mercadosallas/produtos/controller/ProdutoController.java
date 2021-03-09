package br.com.mercadosallas.produtos.controller;

import br.com.mercadosallas.produtos.dto.*;
import br.com.mercadosallas.produtos.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    @PostMapping
    @Transactional
    public ResponseEntity<ProdutoDto> adicionarProduto(@RequestBody @Valid ProdutoForm produtoForm) {

        ProdutoDto produtoDto = produtoService.adicionarProduto(produtoForm);

        return ResponseEntity.status(HttpStatus.CREATED).body(produtoDto);
    }

    @GetMapping
    public ResponseEntity<List<ProdutoDto>> listarProdutos() {

        List<ProdutoDto> produtos = produtoService.listarProdutos();

        return ResponseEntity.status(HttpStatus.OK).body(produtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> listarProdutoPorId(@PathVariable Long id) {

        ProdutoDto produtoDto = produtoService.listarProdutoPorId(id);

        return ResponseEntity.status(HttpStatus.OK).body(produtoDto);
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<ProdutoDto> alterarProduto(@PathVariable Long id, @RequestBody ProdutoAtualizacaoForm form) {
        ProdutoDto produtoDto = produtoService.alterarDadosProdutos(id, form);
        return ResponseEntity.status(HttpStatus.OK).body(produtoDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletarProduto(@PathVariable Long id) {
        produtoService.deletarProduto(id);
        return ResponseEntity.noContent().build();
    }
}
