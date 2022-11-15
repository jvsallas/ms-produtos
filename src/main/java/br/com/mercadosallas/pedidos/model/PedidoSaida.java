package br.com.mercadosallas.pedidos.model;

import br.com.mercadosallas.produtos.dto.ProdutoDto;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class PedidoSaida {
    private Long id;
    private String idCliente;
    private List<ProdutoDto> produtos;
    private Double valorCompra;
    private String statusPagamento;
    private String statusEntrega;
    private LocalDate dataCompra;
    private Integer qtdProdutos;
}
