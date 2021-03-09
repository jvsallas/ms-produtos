package br.com.mercadosallas.telefones.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class TelefoneForm {
    @NotNull
    @NotEmpty
    @Length(min = 2, max = 3)
    private String ddd;

    @NotNull
    @NotEmpty
    @Length(min = 8, max = 9)
    private String numero;

    @Length(max = 20)
    private String tipo;
}
