package br.com.mercadosallas.produtos.exceptions;

import lombok.Getter;

@Getter
public class ProdutoNotFoundException extends RuntimeException {
    private static final long serialVersionUID = -4200078184605195077L;

    private String message;

    public ProdutoNotFoundException(String message) {
        super(message);
        this.message = message;
    }
}
