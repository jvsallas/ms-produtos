package br.com.mercadosallas.pedidos.exception;

import lombok.Getter;

@Getter
public class PedidoCompraNotFoundException extends RuntimeException {
    private static final long serialVersionUID = -9122837982520528703L;

    private String message;

    public PedidoCompraNotFoundException(String message) {
        super(message);
        this.message = message;
    }
}
