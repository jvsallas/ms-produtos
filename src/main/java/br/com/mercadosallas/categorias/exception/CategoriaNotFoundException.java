package br.com.mercadosallas.categorias.exception;

import lombok.Getter;

@Getter
public class CategoriaNotFoundException extends RuntimeException {
    private static final long serialVersionUID = -9122837982520528703L;

    private String message;

    public CategoriaNotFoundException(String message) {
        super(message);
        this.message = message;
    }
}
