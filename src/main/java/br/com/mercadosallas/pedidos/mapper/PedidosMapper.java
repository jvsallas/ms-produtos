package br.com.mercadosallas.pedidos.mapper;

import br.com.mercadosallas.pedidos.model.PedidosEntity;
import br.com.mercadosallas.pedidos.model.PedidoSaida;
import br.com.mercadosallas.produtos.mapper.ProdutoMapper;
import br.com.mercadosallas.produtos.model.ProdutoEntity;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class PedidosMapper {

    public static PedidosEntity mapToEntity(String idCliente, List<ProdutoEntity> produtos,
                                            String statusAguardandoPagamento, String statusPagamentoPendente, Double valorTotalCompra,
                                            Integer qtdProdutos){


        PedidosEntity pedidosEntity = new PedidosEntity();
        pedidosEntity.setIdCliente(idCliente);
        pedidosEntity.setProdutos(produtos);
        pedidosEntity.setStatusPagamento(statusPagamentoPendente);
        pedidosEntity.setStatusEntrega(statusAguardandoPagamento);
        pedidosEntity.setDataCompra(LocalDate.now());
        pedidosEntity.setValorCompra(valorTotalCompra);
        pedidosEntity.setQtdProdutos(qtdProdutos);

        return pedidosEntity;
    }


    public static PedidoSaida mapToDto(PedidosEntity pedidosEntity){

        PedidoSaida pedidoSaida = new PedidoSaida();
        pedidoSaida.setId(pedidosEntity.getId());
        pedidoSaida.setIdCliente(pedidosEntity.getIdCliente());
        pedidoSaida.setProdutos(ProdutoMapper.mapToListDto(pedidosEntity.getProdutos()));
        pedidoSaida.setQtdProdutos(pedidosEntity.getQtdProdutos());
        pedidoSaida.setStatusEntrega(pedidosEntity.getStatusEntrega());
        pedidoSaida.setStatusPagamento(pedidosEntity.getStatusPagamento());
        pedidoSaida.setValorCompra(pedidosEntity.getValorCompra());
        pedidoSaida.setDataCompra(pedidosEntity.getDataCompra());

        return pedidoSaida;
    }

    public static List<PedidoSaida> mapToListDto(List<PedidosEntity> carrinhos){
        return carrinhos.stream().map(PedidosMapper::mapToDto).collect(Collectors.toList());
    }

}
