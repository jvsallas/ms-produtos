package br.com.mercadosallas.categorias.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity(name = "categoria")
public class CategoriaEntity {

    @Id
    @Column(name = "id", updatable = false, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome_categoria")
    private String nomeCategoria;

}
