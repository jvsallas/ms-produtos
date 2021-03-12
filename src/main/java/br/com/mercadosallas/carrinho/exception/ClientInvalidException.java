package br.com.mercadosallas.carrinho.exception;

import lombok.Getter;

@Getter
public class ClientInvalidException extends RuntimeException {
    private static final long serialVersionUID = -3962670062178534839L;

    public ClientInvalidException(String message) {
        super(message);
    }
}
