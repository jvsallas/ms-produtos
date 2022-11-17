package br.com.mercadosallas.pedidos.model;

public enum EnumPedidoStatusPagamento {
    PENDENTE(1,"Pendente"),
    PAGO(2,"Pago");

    private final int id;
    private final String descricao;

    EnumPedidoStatusPagamento(int id, String status) {
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
