package br.com.mercadosallas.pedidos.exception;

import lombok.Getter;

@Getter
public class PedidoNotFoundException extends RuntimeException {
    private static final long serialVersionUID = -4150130680655590567L;

    public PedidoNotFoundException(String message) {
        super(message);
    }
}
