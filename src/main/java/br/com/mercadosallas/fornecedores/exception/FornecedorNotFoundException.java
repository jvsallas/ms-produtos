package br.com.mercadosallas.fornecedores.exception;

import lombok.Getter;

@Getter
public class FornecedorNotFoundException extends RuntimeException {
    private static final long serialVersionUID = -9122837982520528703L;

    private String message;

    public FornecedorNotFoundException(String message) {
        super(message);
        this.message = message;
    }
}
