package br.com.mercadosallas.telefones.controller;

import br.com.mercadosallas.telefones.dto.TelefoneAtualizacaoForm;
import br.com.mercadosallas.telefones.dto.TelefoneDto;
import br.com.mercadosallas.telefones.dto.TelefoneForm;
import br.com.mercadosallas.telefones.service.TelefoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/fornecedores/{idFornecedor}/telefones")
public class TelefoneController {

    @Autowired
    private TelefoneService telefoneService;

    @PostMapping
    @Transactional
    public ResponseEntity<TelefoneDto> adicionarTelefoneAoFornecedor(@PathVariable Long idFornecedor,
                                                                     @RequestBody TelefoneForm form) {
        TelefoneDto telefoneDto = telefoneService.adicionarTelefoneAoFornecedor(idFornecedor, form);
        return ResponseEntity.status(HttpStatus.CREATED).body(telefoneDto);
    }

    @GetMapping
    public ResponseEntity<List<TelefoneDto>> listarTelefonesDoFornecedor(@PathVariable Long idFornecedor) {
        List<TelefoneDto> telefones = telefoneService.listarTelefonesDoFornecedor(idFornecedor);
        return ResponseEntity.status(HttpStatus.OK).body(telefones);
    }

    @GetMapping("/{idTelefone}")
    public ResponseEntity<TelefoneDto> listarTelefonePorId(@PathVariable Long idFornecedor, @PathVariable Long idTelefone) {
        TelefoneDto telefone = telefoneService.listarTelefonePorId(idFornecedor, idTelefone);
        return ResponseEntity.status(HttpStatus.OK).body(telefone);
    }

    @PutMapping("/{idTelefone}")
    @Transactional
    public ResponseEntity<TelefoneDto> alterarNumeroTelefone(@PathVariable Long idFornecedor, @PathVariable Long idTelefone,
                                                             @RequestBody TelefoneAtualizacaoForm form) {
        TelefoneDto telefoneDto = telefoneService.alterarTelefone(idFornecedor, idTelefone, form);
        return ResponseEntity.status(HttpStatus.OK).body(telefoneDto);
    }

    @DeleteMapping("/{idTelefone}")
    @Transactional
    public ResponseEntity<?> deletarTelefone(@PathVariable Long idFornecedor, @PathVariable Long idTelefone) {
        telefoneService.deletarTelefone(idFornecedor, idTelefone);
        return ResponseEntity.noContent().build();
    }
}
