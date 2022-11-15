package br.com.mercadosallas.produtos.exceptions.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class ErroDto {
    private String erro;
    private String message;

    public ErroDto(String erro) {
        this.erro = erro;
    }
}
