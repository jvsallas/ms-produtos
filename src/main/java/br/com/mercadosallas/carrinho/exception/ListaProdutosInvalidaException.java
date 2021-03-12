package br.com.mercadosallas.carrinho.exception;

import lombok.Getter;

@Getter
public class ListaProdutosInvalidaException extends RuntimeException {
    private static final long serialVersionUID = -4868375580236425617L;

    public ListaProdutosInvalidaException(String message) {
        super(message);
    }
}
