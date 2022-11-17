package br.com.mercadosallas.pedidos.controller;

import br.com.mercadosallas.pedidos.facade.PedidoFacade;
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

    @PutMapping("/pagamentos/{id_cliente}/{id_pedido}")
    public PedidoSaida pagarCompra(@PathVariable(name = "id_cliente") String idCliente, @PathVariable(name = "id_pedido") Long idPedido) {
        return pedidoFacade.pagarCompra(idCliente, idPedido);
    }

    @PutMapping("/entrega/{idCliente}/{idCompra}")
    public PedidoSaida entregarCompra(@PathVariable String idCliente, @PathVariable Long idCompra) {
        return pedidoFacade.entregarCompra(idCliente, idCompra);
    }

}