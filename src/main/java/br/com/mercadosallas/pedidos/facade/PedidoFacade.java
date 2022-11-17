package br.com.mercadosallas.pedidos.facade;


import br.com.mercadosallas.pedidos.exception.*;
import br.com.mercadosallas.pedidos.mapper.PedidosMapper;
import br.com.mercadosallas.pedidos.model.*;
import br.com.mercadosallas.pedidos.repository.PedidoRepository;
import br.com.mercadosallas.produtos.model.ProdutoEntity;
import br.com.mercadosallas.produtos.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PedidoFacade {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private PedidoRepository pedidoRepository;

    private final static String STATUS_AGUARDANDO_PAGAMENTO = "Aguardando Pagamento";
    private final static String STATUS_PAGAMENTO_PENDENTE = "Pendente";

    public PedidoSaida realizarPedido(PedidoEntrada pedidoEntrada) {

        List<ProdutoEntity> produtos = produtoRepository.findAllById(pedidoEntrada.getProdutos());

        if (produtos.isEmpty())
            throw new PedidoCompraNotFoundException("Produto(s) selecionados não encontrado.");

        Double valorTotalPedido = 0D;

        for (ProdutoEntity produto : produtos) {
            valorTotalPedido += produto.getPreco();
        }

        PedidosEntity pedidosEntity = PedidosMapper.mapToEntity(
                pedidoEntrada.getIdCliente(), produtos,
                STATUS_AGUARDANDO_PAGAMENTO, STATUS_PAGAMENTO_PENDENTE,
                valorTotalPedido, produtos.size());

        pedidosEntity = pedidoRepository.save(pedidosEntity);

        return PedidosMapper.mapToDto(pedidosEntity);
    }

    public PedidoSaida buscarPedido(Long id) {
        Optional<PedidosEntity> optRetornoBanco = pedidoRepository.findById(id);

        PedidosEntity pedidoEntity = optRetornoBanco.orElseThrow(() -> new PedidoCompraNotFoundException("Pedido não encontrado"));

        return PedidosMapper.mapToDto(pedidoEntity);
    }

    public ExtratoSaida consultarPedidosRealizadosPorPeriodoComStatusPagemento(LocalDate dataInicio, LocalDate dataFim, String statusPagamento) {

        if (dataInicio == null || dataFim == null || dataFim.compareTo(dataInicio) < 0)
            throw new DataInvalidaException("Data Invalida.");

        List<PedidosEntity> pedidos = pedidoRepository.findAll();

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

        List<PedidosEntity> pedidos = pedidoRepository.findAll();

        if (filtroStatusEntrega.isEmpty())
            return PedidosMapper.mapToListDto(pedidos);

        return pedidos.stream()
                .filter(pedido -> pedido.getStatusEntrega().equals(filtroStatusEntrega))
                .map(PedidosMapper::mapToDto)
                .collect(Collectors.toList());
    }


    public PedidoSaida pagarCompra(String idCliente, Long idCompra) {

        PedidosEntity entidade = buscarPedido(idCliente, idCompra);

        if (StatusPagamentoPedido.PAGO.getDescricao().equals(entidade.getStatusPagamento()))
            throw new PagamentoJaRealizadoException("O pagamento da compra informada ja foi realizado. - Operacao Cancelada.");

        entidade.setStatusPagamento(StatusPagamentoPedido.PAGO.getDescricao());
        entidade.setStatusEntrega(StatusEntregaPedido.EM_ROTA.getDescricao());

        pedidoRepository.save(entidade);

        return PedidosMapper.mapToDto(entidade);
    }


    public PedidoSaida entregarCompra(String idCliente, Long idCompra) {

        PedidosEntity entidade = buscarPedido(idCliente, idCompra);

        if (StatusEntregaPedido.EM_ROTA.getDescricao().equals(entidade.getStatusEntrega()))
            entidade.setStatusEntrega(StatusEntregaPedido.ENTREGUE.getDescricao());
        else if (StatusPagamentoPedido.PENDENTE.getDescricao().equals(entidade.getStatusPagamento()))
            throw new StatusInvalidoException("Pagamento pendente.");
        else
            throw new StatusInvalidoException("A compra informada ja foi entregue.");

        pedidoRepository.save(entidade);

        return PedidosMapper.mapToDto(entidade);
    }

    public PedidosEntity buscarPedido(String idCliente, Long idCompra) {

        Optional<PedidosEntity> optPedidoEntity = pedidoRepository.findById(idCompra);

        PedidosEntity pedidosEntity = optPedidoEntity
                .orElseThrow(() -> new PedidoNotFoundException("Pedido não encontrado."));

        if (!pedidosEntity.getIdCliente().equals(idCliente))
            throw new ClientInvalidException("ID cliente invalido.");

        return pedidosEntity;
    }

}
