package br.com.mercadosallas.fornecedores.exception;

import lombok.Getter;

@Getter
public class FornecedorAlreadyExistsException extends RuntimeException {
    private static final long serialVersionUID = 4166719467898347331L;

    private String message;

    public FornecedorAlreadyExistsException(String message) {
        super(message);
        this.message = message;
    }
}
