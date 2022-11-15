package br.com.mercadosallas.handler;

import br.com.mercadosallas.carrinho.exception.*;
import br.com.mercadosallas.categorias.exception.CategoriaAlreadyExistsException;
import br.com.mercadosallas.fornecedores.exception.FornecedorAlreadyExistsException;
import br.com.mercadosallas.fornecedores.exception.FornecedorNotFoundException;
import br.com.mercadosallas.produtos.exceptions.dto.ErroDto;
import br.com.mercadosallas.produtos.exceptions.dto.ErroFormularioDto;
import br.com.mercadosallas.categorias.exception.CategoriaNotFoundException;
import br.com.mercadosallas.produtos.exceptions.ProdutoAlreadyExistsException;
import br.com.mercadosallas.produtos.exceptions.ProdutoNotFoundException;
import br.com.mercadosallas.telefones.exception.TelefoneNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.DateTimeException;
import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class ErrorInterceptorHandler {

    @Autowired
    private MessageSource messageSource;

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public List<ErroFormularioDto> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        List<ErroFormularioDto> errosForm = new ArrayList<>();

        List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();

        fieldErrors.forEach(fieldError -> {
            String mensagem = messageSource.getMessage(fieldError, LocaleContextHolder.getLocale());

            ErroFormularioDto erroForm = new ErroFormularioDto(fieldError.getField(), mensagem);

            errosForm.add(erroForm);
        });

        return errosForm;
    }

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(DateTimeException.class)
    public String handleDateTimeException(DateTimeException exception) {
        return "Ocorreu um erro ao formatar a data";
    }

    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ErroDto handleException(Exception exception) {
        return new ErroDto("Ocorreu erro um inesperado: " + exception.getMessage());
    }

    @ResponseStatus(code = HttpStatus.CONFLICT)
    @ExceptionHandler(CategoriaAlreadyExistsException.class)
    public ErroDto handleCategoriaAlreadyExistsException(CategoriaAlreadyExistsException exception) {
        return new ErroDto(exception.getMessage());
    }

    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    @ExceptionHandler(CategoriaNotFoundException.class)
    public ErroDto handleCategoriaNotFoundException(CategoriaNotFoundException exception) {
        return new ErroDto(exception.getMessage());
    }

    @ResponseStatus(code = HttpStatus.CONFLICT)
    @ExceptionHandler(FornecedorAlreadyExistsException.class)
    public ErroDto handleFornecedorAlreadyExistsException(FornecedorAlreadyExistsException exception) {
        return new ErroDto(exception.getMessage());
    }

    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    @ExceptionHandler(FornecedorNotFoundException.class)
    public ErroDto handleFornecedorNotFoundException(FornecedorNotFoundException exception) {
        return new ErroDto(exception.getMessage());
    }

    @ResponseStatus(code = HttpStatus.CONFLICT)
    @ExceptionHandler(ProdutoAlreadyExistsException.class)
    public ErroDto handleProdutoAlreadyExistsException(ProdutoAlreadyExistsException exception) {
        return new ErroDto(exception.getMessage());
    }

    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    @ExceptionHandler(ProdutoNotFoundException.class)
    public ErroDto handleProdutoNotFoundException(ProdutoNotFoundException exception) {
        return new ErroDto(exception.getMessage());
    }

    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    @ExceptionHandler(TelefoneNotFoundException.class)
    public ErroDto handleTelefoneNotFoundException(TelefoneNotFoundException exception) {
        return new ErroDto(exception.getMessage());
    }


    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    @ExceptionHandler(CarrinhoNotFoundException.class)
    public ErroDto handleTelefoneCarrinhoNotFoundException(CarrinhoNotFoundException exception) {
        return new ErroDto(exception.getMessage());
    }

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ClientInvalidException.class)
    public ErroDto handleClientInvalidException(ClientInvalidException exception) {
        return new ErroDto(exception.getMessage());
    }

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(DataInvalidaException.class)
    public ErroDto handleDataInvalidaException(DataInvalidaException exception) {
        return new ErroDto(exception.getMessage());
    }

    @ResponseStatus(code = HttpStatus.CONFLICT)
    @ExceptionHandler(PagamentoJaRealizadoException.class)
    public ErroDto handlePagamentoJaRealizadoException(PagamentoJaRealizadoException exception) {
        return new ErroDto(exception.getMessage());
    }

    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    @ExceptionHandler(PedidoCompraNotFoundException.class)
    public ErroDto handlePedidoCompraNotFoundException(PedidoCompraNotFoundException exception) {
        return new ErroDto(exception.getMessage());
    }

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ListaProdutosInvalidaException.class)
    public ErroDto handleListaProdutosInvalidaException(ListaProdutosInvalidaException exception) {
        return new ErroDto(exception.getMessage());
    }



}
