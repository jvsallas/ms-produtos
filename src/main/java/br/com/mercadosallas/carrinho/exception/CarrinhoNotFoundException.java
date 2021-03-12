package br.com.mercadosallas.carrinho.exception;

import lombok.Getter;

@Getter
public class CarrinhoNotFoundException extends RuntimeException {
    private static final long serialVersionUID = -4150130680655590567L;

    public CarrinhoNotFoundException(String message) {
        super(message);
    }
}
