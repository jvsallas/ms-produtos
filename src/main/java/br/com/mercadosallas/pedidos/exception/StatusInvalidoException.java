package br.com.mercadosallas.pedidos.exception;

import lombok.Getter;

@Getter
public class StatusInvalidoException extends RuntimeException {
    private static final long serialVersionUID = 1368741784406957907L;

    public StatusInvalidoException(String message) {
        super(message);
    }
}
