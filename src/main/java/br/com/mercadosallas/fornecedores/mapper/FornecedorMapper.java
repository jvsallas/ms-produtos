package br.com.mercadosallas.fornecedores.mapper;

import br.com.mercadosallas.fornecedores.dto.FornecedorDto;
import br.com.mercadosallas.fornecedores.dto.FornecedorForm;
import br.com.mercadosallas.fornecedores.model.FornecedorEntity;
import br.com.mercadosallas.telefones.mapper.TelefoneMapper;

import java.util.List;
import java.util.stream.Collectors;

public class FornecedorMapper {

    public static FornecedorEntity mapToEntity(FornecedorForm fornecedorForm){

        FornecedorEntity fornecedorEntity = new FornecedorEntity();
        fornecedorEntity.setNome(fornecedorForm.getNome());
        fornecedorEntity.setCnpj(fornecedorForm.getCnpj());
        fornecedorEntity.setEmail(fornecedorForm.getEmail());
        fornecedorEntity.setTelefones(TelefoneMapper.mapToListEntity(fornecedorForm.getTelefones()));

        return fornecedorEntity;
    }


    public static FornecedorDto mapToDto(FornecedorEntity fornecedorEntity){

        FornecedorDto fornecedorDto = new FornecedorDto();
        fornecedorDto.setNome(fornecedorEntity.getNome());
        fornecedorDto.setCnpj(fornecedorEntity.getCnpj());
        fornecedorDto.setEmail(fornecedorEntity.getEmail());
        fornecedorDto.setTelefones(TelefoneMapper.mapToListDto(fornecedorEntity.getTelefones()));

        return fornecedorDto;
    }

    public static List<FornecedorEntity> mapToListEntity(List<FornecedorForm> telefones){
        return telefones.stream().map(FornecedorMapper::mapToEntity).collect(Collectors.toList());
    }

    public static List<FornecedorDto> mapToListDto(List<FornecedorEntity> telefones){
        return telefones.stream().map(FornecedorMapper::mapToDto).collect(Collectors.toList());
    }

}
