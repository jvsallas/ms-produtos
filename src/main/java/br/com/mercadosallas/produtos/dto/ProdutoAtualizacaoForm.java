package br.com.mercadosallas.produtos.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Min;
import java.util.List;

@Setter
@Getter
@ToString
public class ProdutoAtualizacaoForm {
    private String nome;
    private String descricao;
    @Min(0)
    private Double preco;
    private List<Long> fornecedores;
    private Long categoria;

}
