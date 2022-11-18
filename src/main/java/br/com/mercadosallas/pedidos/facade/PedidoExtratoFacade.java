package br.com.mercadosallas.pedidos.facade;


import br.com.mercadosallas.pedidos.exception.*;
import br.com.mercadosallas.pedidos.mapper.PedidosMapper;
import br.com.mercadosallas.pedidos.model.*;
import br.com.mercadosallas.pedidos.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PedidoExtratoFacade {

    @Autowired
    private PedidoRepository pedidoRepository;

    public ExtratoSaida extrairPedidosRealizadosPorPeriodoComStatusPagemento(LocalDate dataInicio, LocalDate dataFim, String statusPagamento) {

        if (dataInicio == null || dataFim == null || dataFim.compareTo(dataInicio) < 0)
            throw new DataInvalidaException("Data Invalida.");

        List<PedidoEntity> pedidos = pedidoRepository.findAll();

        List<PedidoSaida> pedidosFeitosNoPeriodo = new ArrayList<>();

        pedidos.forEach(pedido -> {
            if (dataInicio.compareTo(pedido.getDataCompra()) <= 0
                    && dataFim.compareTo(pedido.getDataCompra()) >= 0) {
                if (statusPagamento.isEmpty())
                    pedidosFeitosNoPeriodo.add(PedidosMapper.mapToDto(pedido));
                else if (pedido.getStatusPagamento().equals(statusPagamento))
                    pedidosFeitosNoPeriodo.add(PedidosMapper.mapToDto(pedido));
            }
        });

        ExtratoSaida extratoSaida = new ExtratoSaida();
        extratoSaida.setPedidos(pedidosFeitosNoPeriodo);

        pedidosFeitosNoPeriodo.forEach(pedido -> {
            extratoSaida.setTotalValorPedidos(extratoSaida.getTotalValorPedidos() + pedido.getValorCompra());
            extratoSaida.setTotalPedidos(extratoSaida.getTotalPedidos() + pedido.getQtdProdutos());
        });

        return extratoSaida;
    }

    public ExtratoSaida extratoPedidosPorStatusEntrega(String statusEntrega) {

        List<PedidoSaida> pedidosNaoEntregues = obterTodosPedidos(statusEntrega);

        ExtratoSaida extrato = new ExtratoSaida();
        extrato.setPedidos(pedidosNaoEntregues);

        pedidosNaoEntregues.forEach(pedidoSaida -> {
            extrato.setTotalValorPedidos(extrato.getTotalValorPedidos() + pedidoSaida.getValorCompra());
            extrato.setTotalPedidos(extrato.getTotalPedidos() + pedidoSaida.getQtdProdutos());
        });

        return extrato;
    }

    public List<PedidoSaida> obterTodosPedidos(String filtroStatusEntrega) {

        List<PedidoEntity> pedidos = pedidoRepository.findAll();

        if (filtroStatusEntrega.isEmpty())
            return PedidosMapper.mapToListDto(pedidos);

        return pedidos.stream()
                .filter(pedido -> pedido.getStatusEntrega().equals(filtroStatusEntrega))
                .map(PedidosMapper::mapToDto)
                .collect(Collectors.toList());
    }


}
