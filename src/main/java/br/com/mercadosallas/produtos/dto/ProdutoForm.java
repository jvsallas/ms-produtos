package br.com.mercadosallas.produtos.dto;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
public class ProdutoForm {

    @NotNull @NotEmpty
    private String nome;

    @NotNull @NotEmpty
    private String descricao;

    @NotNull @Min(0)
    private Double preco;

    @NotNull @NotEmpty @Size(min = 1)
    private List<Long> fornecedores;

    @NotNull
    private Long categoria;

}
