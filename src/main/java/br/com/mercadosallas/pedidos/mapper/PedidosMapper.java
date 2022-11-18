package br.com.mercadosallas.pedidos.mapper;

import br.com.mercadosallas.pedidos.model.PedidoEntity;
import br.com.mercadosallas.pedidos.model.PedidoSaida;
import br.com.mercadosallas.produtos.mapper.ProdutoMapper;
import br.com.mercadosallas.produtos.model.ProdutoEntity;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class PedidosMapper {

    public static PedidoEntity mapToEntity(String idCliente, List<ProdutoEntity> produtos,
                                           String statusAguardandoPagamento, String statusPagamentoPendente, Double valorTotalCompra,
                                           Integer qtdProdutos){


        PedidoEntity pedidoEntity = new PedidoEntity();
        pedidoEntity.setIdCliente(idCliente);
        pedidoEntity.setProdutos(produtos);
        pedidoEntity.setStatusPagamento(statusPagamentoPendente);
        pedidoEntity.setStatusEntrega(statusAguardandoPagamento);
        pedidoEntity.setDataCompra(LocalDate.now());
        pedidoEntity.setValorCompra(valorTotalCompra);
        pedidoEntity.setQtdProdutos(qtdProdutos);

        return pedidoEntity;
    }


    public static PedidoSaida mapToDto(PedidoEntity pedidoEntity){

        PedidoSaida pedidoSaida = new PedidoSaida();
        pedidoSaida.setId(pedidoEntity.getId());
        pedidoSaida.setIdCliente(pedidoEntity.getIdCliente());
        pedidoSaida.setProdutos(ProdutoMapper.mapToListDto(pedidoEntity.getProdutos()));
        pedidoSaida.setQtdProdutos(pedidoEntity.getQtdProdutos());
        pedidoSaida.setStatusEntrega(pedidoEntity.getStatusEntrega());
        pedidoSaida.setStatusPagamento(pedidoEntity.getStatusPagamento());
        pedidoSaida.setValorCompra(pedidoEntity.getValorCompra());
        pedidoSaida.setDataCompra(pedidoEntity.getDataCompra());

        return pedidoSaida;
    }

    public static List<PedidoSaida> mapToListDto(List<PedidoEntity> carrinhos){
        return carrinhos.stream().map(PedidosMapper::mapToDto).collect(Collectors.toList());
    }

}
