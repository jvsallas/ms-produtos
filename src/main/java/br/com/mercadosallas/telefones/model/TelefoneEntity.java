package br.com.mercadosallas.telefones.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity(name = "telefone")
public class TelefoneEntity {

    @Id
    @Column(name = "id", updatable = false, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ddd")
    private String ddd;

    @Column(name = "numero")
    private String numero;

    @Column(name = "tipo")
    private String tipo;

    @Column(name = "id_fornecedor")
    private Long idFornecedor;

}
