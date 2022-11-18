package br.com.mercadosallas.pedidos.repository;

import br.com.mercadosallas.pedidos.model.PedidoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoRepository extends JpaRepository<PedidoEntity, Long> {

}

