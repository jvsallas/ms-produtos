package br.com.mercadosallas.carrinho.controller;

import br.com.mercadosallas.carrinho.facade.CarrinhoFacade;
import br.com.mercadosallas.carrinho.model.CarrinhoEntrada;
import br.com.mercadosallas.carrinho.model.CarrinhoSaida;
import br.com.mercadosallas.carrinho.model.ConsultaSaida;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "sistema/carrinho", produces = "application/json")
@Configuration
@CrossOrigin
public class CarrinhoController {

    @Autowired
    private CarrinhoFacade carrinhoFacade;

    @PostMapping("{idCliente}/realizarPedidoCompra")
    public CarrinhoSaida realizarPedidoCompra(@PathVariable String idCliente, @RequestBody CarrinhoEntrada carrinhoEntrada) throws Exception {
        return carrinhoFacade.realizarPedidoCompra(idCliente, carrinhoEntrada);
    }

    @GetMapping("/buscarPedido/{id}")
    public CarrinhoSaida buscarPedido(@PathVariable Long id) throws Exception {
        return carrinhoFacade.buscarPedido(id);
    }

//    @GetMapping("/extratoComprasPeriodo")
//    public ConsultaSaida consultarComprasRealizadasPorPeriodo(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE,pattern = "yyyy-MM-dd") Date dataInicio, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE,pattern = "yyyy-MM-dd")Date dataFim) throws Exception {
//        return carrinhoFacade.consultarComprasRealizadasPorPeriodo(dataInicio,dataFim);
//    }

    @GetMapping("/extratoEntregasPendentes") //ALTERADO***************************************
    public ConsultaSaida consultarEntregasPendentes() throws Exception {
        return carrinhoFacade.consultarEntregasPendentes();
    }

    @GetMapping("/extratoTodosPedidosECompras")
    public List<CarrinhoSaida> consultarTodosPedidosECompras() throws Exception {
        return carrinhoFacade.consultarTodosPedidosECompras();
    }

    @PutMapping("/pagarCompra/{idCliente}/{idCompra}")
    public CarrinhoSaida pagarCompra(@PathVariable String idCliente, @PathVariable Long idCompra) throws Exception {
        return carrinhoFacade.pagarCompra(idCliente, idCompra);
    }

    @PutMapping("/entregarCompra/{idCliente}/{idCompra}")
    public CarrinhoSaida entregarCompra(@PathVariable String idCliente, @PathVariable Long idCompra) throws Exception {
        return carrinhoFacade.entregarCompra(idCliente, idCompra);
    }

}