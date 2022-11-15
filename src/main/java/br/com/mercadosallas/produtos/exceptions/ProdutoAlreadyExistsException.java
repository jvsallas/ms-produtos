package br.com.mercadosallas.produtos.exceptions;

import lombok.Getter;

@Getter
public class ProdutoAlreadyExistsException extends RuntimeException {
    private static final long serialVersionUID = 232676638423210432L;

    private String message;

    public ProdutoAlreadyExistsException(String message) {
        super(message);
        this.message = message;
    }
}
