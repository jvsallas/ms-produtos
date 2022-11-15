package br.com.mercadosallas.produtos.model;

import br.com.mercadosallas.categorias.model.CategoriaEntity;
import br.com.mercadosallas.fornecedores.model.FornecedorEntity;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Data
@Entity(name = "produto")
public class ProdutoEntity {

    @Id
    @Column(name = "id", updatable = false, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "descricao")
    private String descricao;

    @Column(name = "preco")
    private Double preco;

    @ManyToMany
    private List<FornecedorEntity> fornecedores;

    @ManyToOne
    private CategoriaEntity categoria;

    @Column(name = "data_cadastro")
    private LocalDate dataCadastro = LocalDate.now();

}
