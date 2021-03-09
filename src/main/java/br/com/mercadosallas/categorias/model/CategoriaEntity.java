package br.com.mercadosallas.categorias.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity(name = "categoria")
public class CategoriaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome_categoria")
    private String nomeCategoria;

}
