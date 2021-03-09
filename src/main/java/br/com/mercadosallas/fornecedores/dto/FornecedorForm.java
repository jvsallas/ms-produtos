package br.com.mercadosallas.fornecedores.dto;

import br.com.mercadosallas.telefones.dto.TelefoneForm;
import br.com.mercadosallas.telefones.model.TelefoneEntity;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
public class FornecedorForm {
    @NotNull
    @NotEmpty
    @Length(min = 2, max = 25)
    private String nome;

    @NotNull
    @NotEmpty
    @Length(min = 2, max = 25)
    private String cnpj;

    @Email
    private String email;

    @Valid
    @Size(min=1, max=5)
    private List<TelefoneForm> telefones;

}
