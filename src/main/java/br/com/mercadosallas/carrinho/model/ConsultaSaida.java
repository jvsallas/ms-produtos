package br.com.mercadosallas.carrinho.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class ConsultaSaida {
    private Integer totalVendas;
    private Double totalValorCompras;
    private List<CarrinhoSaida> vendas;

    public ConsultaSaida() {
        totalVendas = 0;
        totalValorCompras = 0D;
    }
}
