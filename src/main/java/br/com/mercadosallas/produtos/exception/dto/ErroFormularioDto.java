package br.com.mercadosallas.produtos.exception.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ErroFormularioDto {
    private String campo;
    private String erro;
}
