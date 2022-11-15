package br.com.mercadosallas.pedidos.facade;


import br.com.mercadosallas.pedidos.exception.*;
import br.com.mercadosallas.pedidos.mapper.PedidosMapper;
import br.com.mercadosallas.pedidos.model.PedidosEntity;
import br.com.mercadosallas.pedidos.model.PedidoEntrada;
import br.com.mercadosallas.pedidos.model.PedidoSaida;
import br.com.mercadosallas.pedidos.model.ExtratoSaida;
import br.com.mercadosallas.pedidos.repository.PedidoRepository;
import br.com.mercadosallas.produtos.model.ProdutoEntity;
import br.com.mercadosallas.produtos.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        Optional<PedidosEntity> retornoBanco = pedidoRepository.findById(id);
        if (!retornoBanco.isPresent())
            throw new PedidoCompraNotFoundException("Pedido não encontrado");

        return PedidosMapper.mapToDto(retornoBanco.get());
    }

    public ExtratoSaida consultarPedidosRealizadosPorPeriodoComStatusPagemento(LocalDate dataInicio, LocalDate dataFim, String statusPagamento) {

        if (dataInicio == null || dataFim == null || dataFim.compareTo(dataInicio) < 0)
            throw new DataInvalidaException("Data Invalida.");

        List<PedidosEntity> retornoPedidosEVendas = pedidoRepository.findAll();

        List<PedidoSaida> listaVendasNoPeriodo = new ArrayList<>();

        for (PedidosEntity pedido : retornoPedidosEVendas) {

            if (dataInicio.compareTo(pedido.getDataCompra()) <= 0
                    && dataFim.compareTo(pedido.getDataCompra()) >= 0) {
                if (statusPagamento.isEmpty())
                    listaVendasNoPeriodo.add(PedidosMapper.mapToDto(pedido));
                else if (pedido.getStatusPagamento().equals(statusPagamento))
                    listaVendasNoPeriodo.add(PedidosMapper.mapToDto(pedido));

            }
        }

        ExtratoSaida extratoSaida = new ExtratoSaida();

        extratoSaida.setVendas(listaVendasNoPeriodo);

        for (PedidoSaida pedidoSaida : listaVendasNoPeriodo) {
            extratoSaida.setTotalValorCompras(extratoSaida.getTotalValorCompras() + pedidoSaida.getValorCompra());
            extratoSaida.setTotalVendas(extratoSaida.getTotalVendas() + pedidoSaida.getQtdProdutos());
        }
        return extratoSaida;
    }

    public ExtratoSaida extratoPedidosPorStatusEntrega(String statusEntrega) {

        List<PedidoSaida> listaVendasNaoEntregue = obterTodosPedidos(statusEntrega);

        ExtratoSaida extrato = new ExtratoSaida();
        extrato.setVendas(listaVendasNaoEntregue);

        for (PedidoSaida pedidoSaida : listaVendasNaoEntregue) {
            extrato.setTotalValorCompras(extrato.getTotalValorCompras() + pedidoSaida.getValorCompra());
            extrato.setTotalVendas(extrato.getTotalVendas() + pedidoSaida.getQtdProdutos());
        }
        return extrato;
    }

    public List<PedidoSaida> obterTodosPedidos(String filtroStatusEntrega) {

        List<PedidosEntity> todosPedidos = pedidoRepository.findAll();

        if (filtroStatusEntrega.isEmpty())
            return PedidosMapper.mapToListDto(todosPedidos);

        List<PedidoSaida> pedidosFiltrados = new ArrayList<>();

        for (PedidosEntity pedidoEntity : todosPedidos) {
            if (pedidoEntity.getStatusEntrega().equals(filtroStatusEntrega))
                pedidosFiltrados.add(PedidosMapper.mapToDto(pedidoEntity));
        }

        return pedidosFiltrados;
    }


    public PedidoSaida pagarCompra(String idCliente, Long idCompra) {

        PedidosEntity entidade = buscarPedido(idCliente, idCompra);

        if (!entidade.getStatusPagamento().equals("Pendente")) {
            throw new PagamentoJaRealizadoException("O pagamento da compra informada ja foi realizado. - Operacao Cancelada.");
        } else {
            entidade.setStatusPagamento("Pago");
            entidade.setStatusEntrega("Em rota");
        }

        pedidoRepository.save(entidade);

        return PedidosMapper.mapToDto(entidade);
    }


    public PedidoSaida entregarCompra(String idCliente, Long idCompra) throws Exception {

        PedidosEntity entidade = buscarPedido(idCliente, idCompra);

        if (entidade.getStatusEntrega().equals("Em rota"))
            entidade.setStatusEntrega("Entregue");
        else if (entidade.getStatusPagamento().equals("Pendente"))
            throw new Exception("Pagamento pendente.");
        else
            throw new Exception("A compra informada ja foi entregue.");

        pedidoRepository.save(entidade);

        return PedidosMapper.mapToDto(entidade);
    }

    public PedidosEntity buscarPedido(String idCliente, Long idCompra) {

        Optional<PedidosEntity> pedidoEntity = pedidoRepository.findById(idCompra);

        if (!pedidoEntity.isPresent())
            throw new PedidoNotFoundException("ID pedido não encontrado.");

        if (!pedidoEntity.get().getIdCliente().equals(idCliente))
            throw new ClientInvalidException("Id cliente invalido.");

        return pedidoEntity.get();
    }

}
