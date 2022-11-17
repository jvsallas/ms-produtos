package br.com.mercadosallas.pedidos.model;

public enum StatusPagamentoPedido {
    PENDENTE(1,"Pendente"),
    PAGO(2,"Pago");

    private final int id;
    private final String descricao;

    StatusPagamentoPedido(int id, String status) {
        this.id = id;
        this.descricao = status;
    }
    public int getId() {
        return this.id;
    }

    public String getDescricao() {
        return this.descricao;
    }
}
