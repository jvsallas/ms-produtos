package br.com.mercadosallas.pedidos.exception;

import lombok.Getter;

@Getter
public class PagamentoJaRealizadoException extends RuntimeException {
    private static final long serialVersionUID = 8922867515977326063L;

    public PagamentoJaRealizadoException(String message) {
        super(message);
    }
}
