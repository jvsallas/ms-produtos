package br.com.mercadosallas.telefones.exception;

import lombok.Getter;

@Getter
public class MinimoTelefoneException extends RuntimeException {
    private static final long serialVersionUID = -3308117885243103609L;

    private String message;

    public MinimoTelefoneException(String message) {
        super(message);
        this.message = message;
    }
}
