package br.com.mercadosallas.pedidos.model;

public enum EnumPedidoStatusEntrega {
    EM_ROTA(1,"Em rota"),
    ENTREGUE(2,"Entregue");

    private final int id;
    private final String descricao;

    EnumPedidoStatusEntrega(int id, String status) {
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
