package br.com.mercadosallas.produtos.mapper;

import br.com.mercadosallas.fornecedores.mapper.FornecedorMapper;
import br.com.mercadosallas.produtos.dto.ProdutoDto;
import br.com.mercadosallas.produtos.dto.ProdutoForm;
import br.com.mercadosallas.produtos.model.ProdutoEntity;
import br.com.mercadosallas.utils.DataUtils;
import br.com.mercadosallas.categorias.mapper.CategoriaMapper;

import java.util.List;
import java.util.stream.Collectors;

public class ProdutoMapper {

    public static ProdutoEntity mapToEntity(ProdutoForm produtoForm){

        ProdutoEntity produtoEntity = new ProdutoEntity();
        produtoEntity.setNome(produtoForm.getNome());
        produtoEntity.setDescricao(produtoForm.getDescricao());
        produtoEntity.setPreco(produtoForm.getPreco());

        return produtoEntity;
    }


    public static ProdutoDto mapToDto(ProdutoEntity produtoEntity){

        ProdutoDto produtoDto = new ProdutoDto();
        produtoDto.setId(produtoEntity.getId());
        produtoDto.setNome(produtoEntity.getNome());
        produtoDto.setDescricao(produtoEntity.getDescricao());
        produtoDto.setPreco(produtoEntity.getPreco());
        produtoDto.setDataCadastro(produtoEntity.getDataCadastro());
        produtoDto.setFornecedores(FornecedorMapper.mapToListDto(produtoEntity.getFornecedores()));
        produtoDto.setCategoria(CategoriaMapper.mapToDto(produtoEntity.getCategoria()));

        return produtoDto;
    }

    public static List<ProdutoDto> mapToListDto(List<ProdutoEntity> clientes){
        return clientes.stream().map(ProdutoMapper::mapToDto).collect(Collectors.toList());
    }

}
