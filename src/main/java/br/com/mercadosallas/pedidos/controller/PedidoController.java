package br.com.mercadosallas.pedidos.controller;

import br.com.mercadosallas.pedidos.facade.PedidoFacade;
import br.com.mercadosallas.pedidos.model.PedidoEntregaRequest;
import br.com.mercadosallas.pedidos.model.PedidoPagamentoRequest;
import br.com.mercadosallas.pedidos.model.PedidoEntrada;
import br.com.mercadosallas.pedidos.model.PedidoSaida;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "pedidos", produces = "application/json")
@Configuration
@CrossOrigin
public class PedidoController {

    @Autowired
    private PedidoFacade pedidoFacade;

    @PostMapping
    public PedidoSaida realizarPedidoCompra(@RequestBody @Valid PedidoEntrada pedidoEntrada) {
        return pedidoFacade.realizarPedido(pedidoEntrada);
    }

    @GetMapping("/{id}")
    public PedidoSaida buscarPedido(@PathVariable Long id) {
        return pedidoFacade.buscarPedido(id);
    }

    @GetMapping
    public List<PedidoSaida> consultarTodosPedidosECompras(@RequestParam(name = "status_entrega", required = false, defaultValue = "") String statusEntrega) {
        return pedidoFacade.obterTodosPedidos(statusEntrega);
    }

    @PatchMapping("/{id_pedido}/pagamentos")
    public PedidoSaida pagarCompra(@PathVariable(name = "id_pedido") Long idPedido, @RequestBody PedidoPagamentoRequest pedidoPagamentoRequest) {
        return pedidoFacade.pagarPedido(idPedido, pedidoPagamentoRequest);
    }

    @PatchMapping("/{id_pedido}/entregas")
    public PedidoSaida entregarCompra(@PathVariable(name = "id_pedido") Long idPedido, @RequestBody PedidoEntregaRequest pedidoEntregaRequest) {
        return pedidoFacade.entregarPedido(idPedido, pedidoEntregaRequest);
    }

}