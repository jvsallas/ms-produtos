package br.com.mercadosallas.categorias.exception;

import lombok.Getter;

@Getter
public class CategoriaAlreadyExistsException extends RuntimeException {
    private static final long serialVersionUID = 4166719467898347331L;

    private String message;

    public CategoriaAlreadyExistsException(String message) {
        super(message);
        this.message = message;
    }
}
