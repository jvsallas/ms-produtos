package br.com.mercadosallas.carrinho.model;

import br.com.mercadosallas.produtos.dto.ProdutoDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CarrinhoSaida {
    private Long id;
    private String idCliente;
    private List<ProdutoDto> produtos;
    private Double valorCompra;
    private String statusPagamento;
    private String statusEntrega;
    private LocalDateTime dataCompra;
    private Integer qtdProdutos;
}
