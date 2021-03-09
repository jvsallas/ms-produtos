package br.com.mercadosallas.fornecedores.repository;

import br.com.mercadosallas.fornecedores.model.FornecedorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface FornecedorRepository extends JpaRepository<FornecedorEntity, Long> {
    Optional<FornecedorEntity> findByNome(String nome);
    Optional<FornecedorEntity> findByCnpj(String cnpj);

}
