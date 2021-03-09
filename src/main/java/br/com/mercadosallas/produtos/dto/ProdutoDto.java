package br.com.mercadosallas.produtos.dto;

import br.com.mercadosallas.categorias.dto.CategoriaDto;
import br.com.mercadosallas.categorias.model.CategoriaEntity;
import br.com.mercadosallas.fornecedores.dto.FornecedorDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@Data
public class ProdutoDto {

    @JsonProperty("id")
    private String id;
    @JsonProperty("nome")
    private String nome;
    @JsonProperty("descricao")
    private String descricao;
    @JsonProperty("preco")
    private Double preco;
    @JsonProperty("fornecedores")
    private List<FornecedorDto> fornecedores;
    @JsonProperty("categoria")
    private CategoriaDto categoria;
    @JsonProperty("data_cadastro")
    private LocalDate dataCadastro = LocalDate.now();


}
