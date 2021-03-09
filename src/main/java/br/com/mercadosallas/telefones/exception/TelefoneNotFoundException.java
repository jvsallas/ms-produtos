package br.com.mercadosallas.telefones.exception;

import lombok.Getter;

@Getter
public class TelefoneNotFoundException extends RuntimeException {
    private static final long serialVersionUID = -9122837982520528703L;

    private String message;

    public TelefoneNotFoundException(String message) {
        super(message);
        this.message = message;
    }
}
