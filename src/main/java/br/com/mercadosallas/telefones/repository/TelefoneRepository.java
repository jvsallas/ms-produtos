package br.com.mercadosallas.telefones.repository;

import br.com.mercadosallas.telefones.model.TelefoneEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface TelefoneRepository extends JpaRepository<TelefoneEntity, Long> {
    @Modifying
    @Query(value = "DELETE FROM TELEFONE t WHERE t.id = ?1", nativeQuery = true)
    void delete(Long idTelefone);

}
