package br.com.mercadosallas.pedidos.controller;

import br.com.mercadosallas.pedidos.facade.PedidoFacade;
import br.com.mercadosallas.pedidos.model.PedidoEntrada;
import br.com.mercadosallas.pedidos.model.PedidoSaida;
import br.com.mercadosallas.pedidos.model.ExtratoSaida;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(path = "pedidos", produces = "application/json")
@Configuration
@CrossOrigin
public class PedidosController {

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

    @GetMapping("/extrato")
    public ExtratoSaida consultarPedidosRealizadasPorPeriodo(@RequestParam(name = "data_inicio") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE, pattern = "yyyy-MM-dd") LocalDate dataInicio,
                                                             @RequestParam(name = "data_fim") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE, pattern = "yyyy-MM-dd") LocalDate dataFim,
                                                             @RequestParam(name = "status_pagamento", required = false, defaultValue = "") String statusPagamento) {
        return pedidoFacade.consultarPedidosRealizadosPorPeriodoComStatusPagemento(dataInicio, dataFim, statusPagamento);
    }

//    @GetMapping("/extrato")
//    public ExtratoSaida consultarPedidosPorStatusEntrega(@RequestParam(name = "status_entrega") String statusEntrega) {
//        return carrinhoFacade.extratoPedidosPorStatusEntrega(statusEntrega);
//    }

    @GetMapping
    public List<PedidoSaida> consultarTodosPedidosECompras(@RequestParam(name = "status_entrega", required = false, defaultValue = "") String statusEntrega) {
        return pedidoFacade.obterTodosPedidos(statusEntrega);
    }

    @PutMapping("/pagamentos/{id_cliente}/{id_pedido}")
    public PedidoSaida pagarCompra(@PathVariable(name = "id_cliente") String idCliente, @PathVariable(name = "id_pedido") Long idPedido) {
        return pedidoFacade.pagarCompra(idCliente, idPedido);
    }

    @PutMapping("/entrega/{idCliente}/{idCompra}")
    public PedidoSaida entregarCompra(@PathVariable String idCliente, @PathVariable Long idCompra) throws Exception {
        return pedidoFacade.entregarCompra(idCliente, idCompra);
    }

}