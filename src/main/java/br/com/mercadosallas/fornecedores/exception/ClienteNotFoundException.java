package br.com.mercadosallas.fornecedores.exception;

import lombok.Getter;

@Getter
public class ClienteNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 4010655958693219577L;

    private String message;

    public ClienteNotFoundException(String message) {
        super(message);
        this.message = message;
    }
}
