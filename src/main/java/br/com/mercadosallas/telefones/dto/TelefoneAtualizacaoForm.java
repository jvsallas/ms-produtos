package br.com.mercadosallas.telefones.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class TelefoneAtualizacaoForm {

    private String ddd;
    private String numero;
    private String tipo;
}
