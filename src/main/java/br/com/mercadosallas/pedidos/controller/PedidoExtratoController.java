package br.com.mercadosallas.pedidos.controller;

import br.com.mercadosallas.pedidos.facade.PedidoExtratoFacade;
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
@RequestMapping(path = "pedidos/extrato", produces = "application/json")
@Configuration
@CrossOrigin
public class PedidoExtratoController {

    @Autowired
    private PedidoExtratoFacade pedidoExtratoFacade;

    @GetMapping
    public ExtratoSaida consultarPedidosRealizadasPorPeriodo(@RequestParam(name = "data_inicio") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE, pattern = "yyyy-MM-dd") LocalDate dataInicio,
                                                             @RequestParam(name = "data_fim") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE, pattern = "yyyy-MM-dd") LocalDate dataFim,
                                                             @RequestParam(name = "status_pagamento", required = false, defaultValue = "") String statusPagamento) {
        return pedidoExtratoFacade.extrairPedidosRealizadosPorPeriodoComStatusPagemento(dataInicio, dataFim, statusPagamento);
    }

//    @GetMapping("/extrato")
//    public ExtratoSaida consultarPedidosPorStatusEntrega(@RequestParam(name = "status_entrega") String statusEntrega) {
//        return carrinhoFacade.extratoPedidosPorStatusEntrega(statusEntrega);
//    }

}