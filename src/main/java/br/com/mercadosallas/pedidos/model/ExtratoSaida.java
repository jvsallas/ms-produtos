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
    private Integer totalPedidos;
    private Double totalValorPedidos;
    private List<PedidoSaida> pedidos;

    public ExtratoSaida() {
        totalPedidos = 0;
        totalValorPedidos = 0D;
    }
}
