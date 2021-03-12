package br.com.mercadosallas.carrinho.exception;

import lombok.Getter;

@Getter
public class DataInvalidaException extends RuntimeException {
    private static final long serialVersionUID = 3047723164411097542L;

    private String message;

    public DataInvalidaException(String message) {
        super(message);
        this.message = message;
    }
}
