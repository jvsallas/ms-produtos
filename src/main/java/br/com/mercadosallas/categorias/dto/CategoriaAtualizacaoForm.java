package br.com.mercadosallas.categorias.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Setter
@Getter
@ToString
public class CategoriaAtualizacaoForm {
    @NotNull
    @NotEmpty
    @Length(min = 2, max = 15)
    private String nome;
}
