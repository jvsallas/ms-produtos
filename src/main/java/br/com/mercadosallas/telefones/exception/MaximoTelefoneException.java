package br.com.mercadosallas.telefones.exception;

import lombok.Getter;

@Getter
public class MaximoTelefoneException extends RuntimeException {
    private static final long serialVersionUID = 4592362287975629929L;

    private String message;

    public MaximoTelefoneException(String message) {
        super(message);
        this.message = message;
    }
}
