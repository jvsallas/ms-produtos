package br.com.mercadosallas.carrinho.repository;

import br.com.mercadosallas.carrinho.model.CarrinhoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarrinhoRepository extends JpaRepository<CarrinhoEntity, Long> {

}

