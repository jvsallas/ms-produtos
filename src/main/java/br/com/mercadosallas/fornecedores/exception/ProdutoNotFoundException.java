package br.com.mercadosallas.fornecedores.exception;

import lombok.Getter;

@Getter
public class ProdutoNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 4010655958693219577L;

    private String message;

    public ProdutoNotFoundException(String message) {
        super(message);
        this.message = message;
    }
}
