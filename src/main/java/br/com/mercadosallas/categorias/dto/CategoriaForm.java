package br.com.mercadosallas.categorias.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class CategoriaForm {
    @NotNull
    @NotEmpty
    @Length(min = 2, max = 25)
    private String nome;
}
