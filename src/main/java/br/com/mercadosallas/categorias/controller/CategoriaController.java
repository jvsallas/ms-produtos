package br.com.mercadosallas.categorias.controller;

import br.com.mercadosallas.categorias.dto.CategoriaAtualizacaoForm;
import br.com.mercadosallas.categorias.dto.CategoriaDto;
import br.com.mercadosallas.categorias.dto.CategoriaForm;
import br.com.mercadosallas.categorias.service.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/categorias")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    @PostMapping
    @Transactional
    public ResponseEntity<CategoriaDto> adicionarCategoria(@Valid @RequestBody CategoriaForm form) {
        CategoriaDto categoriaDto = categoriaService.adicionarCategoria(form);
        return ResponseEntity.status(HttpStatus.CREATED).body(categoriaDto);
    }

    @GetMapping
    public ResponseEntity<List<CategoriaDto>> listarCategorias() {
        List<CategoriaDto> categorias = categoriaService.listarCategorias();
        return ResponseEntity.status(HttpStatus.OK).body(categorias);
    }

    @GetMapping("/{idCategoria}")
    public ResponseEntity<CategoriaDto> listarCategoriaPorId(@PathVariable Long idCategoria) {
        CategoriaDto categoria = categoriaService.listarCategoriaPorId(idCategoria);
        return ResponseEntity.status(HttpStatus.OK).body(categoria);
    }

    @PutMapping("/{idCategoria}")
    @Transactional
    public ResponseEntity<CategoriaDto> alterarCategoria(@PathVariable Long idCategoria,
                                                         @RequestBody CategoriaAtualizacaoForm form) {
        CategoriaDto categoriaDto = categoriaService.alterarCategoria(idCategoria, form);
        return ResponseEntity.status(HttpStatus.OK).body(categoriaDto);
    }

    @DeleteMapping("/{idCategoria}")
    @Transactional
    public ResponseEntity<?> deletarCategoria(@PathVariable Long idCategoria) {
        categoriaService.deletarCategoria(idCategoria);
        return ResponseEntity.noContent().build();
    }
}
