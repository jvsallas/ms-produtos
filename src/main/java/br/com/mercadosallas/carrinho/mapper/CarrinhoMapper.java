package br.com.mercadosallas.carrinho.mapper;

import br.com.mercadosallas.carrinho.model.CarrinhoEntity;
import br.com.mercadosallas.carrinho.model.CarrinhoEntrada;
import br.com.mercadosallas.carrinho.model.CarrinhoSaida;
import br.com.mercadosallas.categorias.mapper.CategoriaMapper;
import br.com.mercadosallas.fornecedores.mapper.FornecedorMapper;
import br.com.mercadosallas.produtos.dto.ProdutoDto;
import br.com.mercadosallas.produtos.dto.ProdutoForm;
import br.com.mercadosallas.produtos.mapper.ProdutoMapper;
import br.com.mercadosallas.produtos.model.ProdutoEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class CarrinhoMapper {

    public static CarrinhoEntity mapToEntity(String idCliente,CarrinhoEntrada carrinhoEntrada, List<ProdutoEntity> produtos,
                                             String statusAguardandoPagamento, String statusPagamentoPendente, Double valorTotalCompra,
                                             Integer qtdProdutos){


        CarrinhoEntity carrinhoEntity = new CarrinhoEntity();
        carrinhoEntity.setIdCliente(idCliente);
        carrinhoEntity.setProdutos(produtos);
        carrinhoEntity.setStatusPagamento(statusPagamentoPendente);
        carrinhoEntity.setStatusEntrega(statusAguardandoPagamento);
        carrinhoEntity.setDataCompra(LocalDate.now());
        carrinhoEntity.setValorCompra(valorTotalCompra);
        carrinhoEntity.setQtdProdutos(qtdProdutos);

        return carrinhoEntity;
    }


    public static CarrinhoSaida mapToDto(CarrinhoEntity carrinhoEntity){

        CarrinhoSaida carrinhoSaida = new CarrinhoSaida();
        carrinhoSaida.setId(carrinhoEntity.getId());
        carrinhoSaida.setIdCliente(carrinhoEntity.getIdCliente());
        carrinhoSaida.setProdutos(ProdutoMapper.mapToListDto(carrinhoEntity.getProdutos()));
        carrinhoSaida.setQtdProdutos(carrinhoEntity.getQtdProdutos());
        carrinhoSaida.setStatusEntrega(carrinhoEntity.getStatusEntrega());
        carrinhoSaida.setStatusPagamento(carrinhoEntity.getStatusPagamento());
        carrinhoSaida.setValorCompra(carrinhoEntity.getValorCompra());
        carrinhoSaida.setDataCompra(carrinhoEntity.getDataCompra());

        return carrinhoSaida;
    }

    public static List<CarrinhoSaida> mapToListDto(List<CarrinhoEntity> carrinhos){
        return carrinhos.stream().map(CarrinhoMapper::mapToDto).collect(Collectors.toList());
    }

}
