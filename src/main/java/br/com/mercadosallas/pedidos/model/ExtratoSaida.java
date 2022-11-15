package br.com.mercadosallas.pedidos.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class ExtratoSaida {
    private Integer totalVendas;
    private Double totalValorCompras;
    private List<PedidoSaida> vendas;

    public ExtratoSaida() {
        totalVendas = 0;
        totalValorCompras = 0D;
    }
}
