package br.com.mercadosallas.fornecedores.dto;

import br.com.mercadosallas.telefones.dto.TelefoneDto;
import lombok.Data;

import java.util.List;

@Data
public class FornecedorDto {
    private Long id;
    private String nome;
    private String cnpj;
    private String email;
    private List<TelefoneDto> telefones;
}
