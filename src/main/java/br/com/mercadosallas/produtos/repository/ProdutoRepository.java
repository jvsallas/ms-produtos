package br.com.mercadosallas.produtos.repository;

import br.com.mercadosallas.produtos.model.ProdutoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProdutoRepository extends JpaRepository<ProdutoEntity, Long> {
    Optional<ProdutoEntity> findByNome(String nome);
}
