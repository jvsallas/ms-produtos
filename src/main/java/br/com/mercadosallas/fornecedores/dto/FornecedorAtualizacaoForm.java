package br.com.mercadosallas.fornecedores.dto;

import br.com.mercadosallas.telefones.dto.TelefoneForm;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Setter
@Getter
@ToString
public class FornecedorAtualizacaoForm {
    private String nome;
    private String cnpj;
    private String email;
    private List<TelefoneForm> telefones;
}
