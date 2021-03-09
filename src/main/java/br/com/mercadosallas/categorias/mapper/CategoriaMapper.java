package br.com.mercadosallas.categorias.mapper;

import br.com.mercadosallas.categorias.dto.CategoriaDto;
import br.com.mercadosallas.categorias.dto.CategoriaForm;
import br.com.mercadosallas.categorias.model.CategoriaEntity;

import java.util.List;
import java.util.stream.Collectors;

public class CategoriaMapper {

    public static CategoriaEntity mapToEntity(CategoriaForm categoriaForm){

        CategoriaEntity categoriaEntity = new CategoriaEntity();
        categoriaEntity.setNomeCategoria(categoriaForm.getNome());

        return categoriaEntity;
    }


    public static CategoriaDto mapToDto(CategoriaEntity categoriaEntity){

        CategoriaDto categoriaDto = new CategoriaDto();
        categoriaDto.setId(categoriaEntity.getId());
        categoriaDto.setNome(categoriaEntity.getNomeCategoria());

        return categoriaDto;
    }

    public static List<CategoriaEntity> mapToListEntity(List<CategoriaForm> telefones){
        return telefones.stream().map(CategoriaMapper::mapToEntity).collect(Collectors.toList());
    }

    public static List<CategoriaDto> mapToListDto(List<CategoriaEntity> telefones){
        return telefones.stream().map(CategoriaMapper::mapToDto).collect(Collectors.toList());
    }

}
