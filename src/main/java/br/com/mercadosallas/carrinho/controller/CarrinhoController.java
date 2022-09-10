package br.com.mercadosallas.carrinho.controller;

import br.com.mercadosallas.carrinho.facade.CarrinhoFacade;
import br.com.mercadosallas.carrinho.model.CarrinhoEntrada;
import br.com.mercadosallas.carrinho.model.CarrinhoSaida;
import br.com.mercadosallas.carrinho.model.ConsultaSaida;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(path = "carrinho", produces = "application/json")
@Configuration
@CrossOrigin
public class CarrinhoController {

    @Autowired
    private CarrinhoFacade carrinhoFacade;

    @PostMapping("/pedido/{idCliente}")
    public CarrinhoSaida realizarPedidoCompra(@PathVariable String idCliente, @RequestBody CarrinhoEntrada carrinhoEntrada) throws Exception {
        return carrinhoFacade.realizarPedidoCompra(idCliente, carrinhoEntrada);
    }

    @GetMapping("/pedido/{id}")
    public CarrinhoSaida buscarPedido(@PathVariable Long id) throws Exception {
        return carrinhoFacade.buscarPedido(id);
    }

    @GetMapping("/extrato")
    public ConsultaSaida consultarComprasRealizadasPorPeriodo(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE,pattern = "yyyy-MM-dd") LocalDate dataInicio, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE,pattern = "yyyy-MM-dd")LocalDate dataFim) throws Exception {
        return carrinhoFacade.consultarComprasRealizadasPorPeriodo(dataInicio,dataFim);
    }

    @GetMapping("/extrato/pendentes")
    public ConsultaSaida consultarEntregasPendentes() {
        return carrinhoFacade.consultarEntregasPendentes();
    }

    @GetMapping("/pedido/extrato")
    public List<CarrinhoSaida> consultarTodosPedidosECompras() {
        return carrinhoFacade.consultarTodosPedidosECompras();
    }

    @PutMapping("/pagamento/{idCliente}/{idCompra}")
    public CarrinhoSaida pagarCompra(@PathVariable String idCliente, @PathVariable Long idCompra) throws Exception {
        return carrinhoFacade.pagarCompra(idCliente, idCompra);
    }

    @PutMapping("/entrega/{idCliente}/{idCompra}")
    public CarrinhoSaida entregarCompra(@PathVariable String idCliente, @PathVariable Long idCompra) throws Exception {
        return carrinhoFacade.entregarCompra(idCliente, idCompra);
    }

}