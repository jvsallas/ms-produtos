package br.com.mercadosallas.pedidos.model;

import br.com.mercadosallas.produtos.model.ProdutoEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "pedido")
public class PedidoEntity {
    @Id
    @Column(name = "id", updatable = false, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_cliente")
    private String idCliente;

    @ManyToMany
    private List<ProdutoEntity> produtos;

    @Column(name = "qtd_produtos")
    private Integer qtdProdutos;

    @Column(name = "valor_compra")
    private Double valorCompra;

    @Column(name = "status_pagamento")
    private String statusPagamento;

    @Column(name = "status_entrega")
    private String statusEntrega;

    @Column(name = "data_compra")
    private LocalDate dataCompra;
}
