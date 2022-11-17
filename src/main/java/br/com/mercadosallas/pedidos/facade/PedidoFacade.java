package br.com.mercadosallas.pedidos.facade;


import br.com.mercadosallas.pedidos.exception.*;
import br.com.mercadosallas.pedidos.mapper.PedidosMapper;
import br.com.mercadosallas.pedidos.model.*;
import br.com.mercadosallas.pedidos.repository.PedidoRepository;
import br.com.mercadosallas.produtos.model.ProdutoEntity;
import br.com.mercadosallas.produtos.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

        List<ProdutoEntity> produtosEncontrados = produtoRepository.findAllById(pedidoEntrada.getProdutos());

        if (produtosEncontrados.isEmpty())
            throw new PedidoCompraNotFoundException("Produto(s) selecionados não encontrado.");

        Double valorTotalPedido = 0D;

        for (ProdutoEntity produto : produtosEncontrados) {
            valorTotalPedido += produto.getPreco();
        }

        PedidosEntity pedidosEntity = PedidosMapper.mapToEntity(
                pedidoEntrada.getIdCliente(), produtosEncontrados,
                STATUS_AGUARDANDO_PAGAMENTO, STATUS_PAGAMENTO_PENDENTE,
                valorTotalPedido, produtosEncontrados.size());

        pedidosEntity = pedidoRepository.save(pedidosEntity);

        return PedidosMapper.mapToDto(pedidosEntity);
    }

    public PedidoSaida buscarPedido(Long id) {
        Optional<PedidosEntity> optRetornoBanco = pedidoRepository.findById(id);

        PedidosEntity pedidoEntity = optRetornoBanco.orElseThrow(() -> new PedidoCompraNotFoundException("Pedido não encontrado"));

        return PedidosMapper.mapToDto(pedidoEntity);
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

        if (EnumPedidoStatusPagamento.PAGO.getDescricao().equals(entidade.getStatusPagamento()))
            throw new PagamentoJaRealizadoException("O pagamento da compra informada ja foi realizado. - Operacao Cancelada.");

        entidade.setStatusPagamento(EnumPedidoStatusPagamento.PAGO.getDescricao());
        entidade.setStatusEntrega(EnumPedidoStatusEntrega.EM_ROTA.getDescricao());

        pedidoRepository.save(entidade);

        return PedidosMapper.mapToDto(entidade);
    }


    public PedidoSaida entregarCompra(String idCliente, Long idCompra) {

        PedidosEntity entidade = buscarPedido(idCliente, idCompra);

        if (EnumPedidoStatusEntrega.EM_ROTA.getDescricao().equals(entidade.getStatusEntrega()))
            entidade.setStatusEntrega(EnumPedidoStatusEntrega.ENTREGUE.getDescricao());
        else if (EnumPedidoStatusPagamento.PENDENTE.getDescricao().equals(entidade.getStatusPagamento()))
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
