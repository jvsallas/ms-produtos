package br.com.mercadosallas.categorias.repository;

import br.com.mercadosallas.categorias.model.CategoriaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CategoriaRepository extends JpaRepository<CategoriaEntity, Long> {
    Optional<CategoriaEntity> findByNomeCategoria(String nomeCategoria);

//    @Modifying
//    @Query(value = "DELETE FROM CATEGORIA c WHERE c.id = ?1", nativeQuery = true)
//    void delete(Long idCategoria);

}
