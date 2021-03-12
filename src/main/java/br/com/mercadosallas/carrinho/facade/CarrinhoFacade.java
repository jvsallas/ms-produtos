package br.com.mercadosallas.carrinho.facade;


import br.com.mercadosallas.carrinho.mapper.CarrinhoMapper;
import br.com.mercadosallas.carrinho.model.CarrinhoEntity;
import br.com.mercadosallas.carrinho.model.CarrinhoEntrada;
import br.com.mercadosallas.carrinho.model.CarrinhoSaida;
import br.com.mercadosallas.carrinho.model.ConsultaSaida;
import br.com.mercadosallas.carrinho.repository.CarrinhoRepository;
import br.com.mercadosallas.produtos.model.ProdutoEntity;
import br.com.mercadosallas.produtos.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CarrinhoFacade {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private CarrinhoRepository carrinhoRepository;

    private final static String STATUS_AGUARDANDO_PAGAMENTO = "Aguardando Pagamento";
    private final static String STATUS_PAGAMENTO_PENDENTE = "Pendente";

    public CarrinhoSaida realizarPedidoCompra(String idCliente, CarrinhoEntrada carrinhoEntrada) throws Exception {

        if (carrinhoEntrada.getProdutos().isEmpty()) {
            throw new Exception("Lista de produto(s) preenchida incorretamente. Por favor, adicione um ou mais produtos ao pedido.");
        }

        List<ProdutoEntity> produtos = produtoRepository.findAllById(carrinhoEntrada.getProdutos());

        if (produtos.isEmpty()) {
            throw new Exception("Produto(s) não encontrado.");
        }

        Double valorCompraTotal = 0D;

        for (ProdutoEntity produto : produtos) {
            valorCompraTotal += produto.getPreco();
        }

        CarrinhoEntity carrinhoEntity = CarrinhoMapper.mapToEntity(
                idCliente, carrinhoEntrada, produtos,
                STATUS_AGUARDANDO_PAGAMENTO, STATUS_PAGAMENTO_PENDENTE,
                valorCompraTotal, produtos.size());

        carrinhoEntity = carrinhoRepository.save(carrinhoEntity);

        return CarrinhoMapper.mapToDto(carrinhoEntity);
    }

    public CarrinhoSaida buscarPedido(Long id) throws Exception {
        CarrinhoEntity carrinhoEntity;
        Optional<CarrinhoEntity> retornoBanco = carrinhoRepository.findById(id);
        if (!retornoBanco.isPresent())
            throw new Exception("Pedido não encontrado");

        return CarrinhoMapper.mapToDto(retornoBanco.get());
    }

    public ConsultaSaida consultarComprasRealizadasPorPeriodo(LocalDate dataInicio, LocalDate dataFim) throws Exception {

        if (dataInicio == null || dataFim == null || dataFim.compareTo(dataInicio) < 0) {
            throw new Exception("Data Invalida.");
        }

        List<CarrinhoEntity> retornoPedidosEVendas = retornarTodasEntidadesDePedidosEVendas();

        List<CarrinhoSaida> listaVendasNoPeriodo = new ArrayList<>();

        for (CarrinhoEntity carrinho : retornoPedidosEVendas) {

            if (dataInicio.compareTo(carrinho.getDataCompra()) <= 0
                    && dataFim.compareTo(carrinho.getDataCompra()) >= 0) {
                if (carrinho.getStatusPagamento().equals("Pago")) {
                    listaVendasNoPeriodo.add(CarrinhoMapper.mapToDto(carrinho));
                }
            }
        }

        if (listaVendasNoPeriodo.isEmpty()) {
            throw new Exception("Não ha vendas no periodo informado");
        }

        ConsultaSaida extratoSaida = new ConsultaSaida();

        extratoSaida.setVendas(listaVendasNoPeriodo);

        for (CarrinhoSaida carrinhoSaida : listaVendasNoPeriodo) {
            extratoSaida.setTotalValorCompras(extratoSaida.getTotalValorCompras() + carrinhoSaida.getValorCompra());
            extratoSaida.setTotalVendas(extratoSaida.getTotalVendas() + carrinhoSaida.getQtdProdutos());
        }
        return extratoSaida;
    }

    public ConsultaSaida consultarEntregasPendentes() throws Exception {

        List<CarrinhoEntity> retornoVendas = retornarTodasEntidadesDePedidosEVendas();

        List<CarrinhoSaida> listaVendasNaoEntregue = new ArrayList<>();

        for (CarrinhoEntity venda : retornoVendas) {
            if (venda.getStatusEntrega().equals("Em rota")) {
                listaVendasNaoEntregue.add(CarrinhoMapper.mapToDto(venda));
            }
        }

        if (listaVendasNaoEntregue.isEmpty()) {
            throw new Exception("Não ha entregas pendentes.");
        }

        ConsultaSaida extrato = new ConsultaSaida();
        extrato.setVendas(listaVendasNaoEntregue);

        for (CarrinhoSaida carrinhoSaida : listaVendasNaoEntregue) {
            extrato.setTotalValorCompras(extrato.getTotalValorCompras() + carrinhoSaida.getValorCompra());
            extrato.setTotalVendas(extrato.getTotalVendas() + carrinhoSaida.getQtdProdutos());
        }
        return extrato;
    }

    public List<CarrinhoEntity> retornarTodasEntidadesDePedidosEVendas() throws Exception {

        List<CarrinhoEntity> retornoPedidosEVendas = carrinhoRepository.findAll();
        if (retornoPedidosEVendas.isEmpty()) {
            throw new Exception("Não ha pedidos/compras realizadas.");
        }

        return retornoPedidosEVendas;
    }

    public List<CarrinhoSaida> consultarTodosPedidosECompras() throws Exception {

        List<CarrinhoEntity> listaCarrinhoSaida = retornarTodasEntidadesDePedidosEVendas();

        return CarrinhoMapper.mapToListDto(listaCarrinhoSaida);
    }


    public CarrinhoSaida pagarCompra(String idCliente, Long idCompra) throws Exception {

        CarrinhoEntity entidade = buscaNoRespositoryPorIdCompraEIdCliente(idCliente, idCompra);

        if (!entidade.getStatusPagamento().equals("Pendente")) {
            throw new Exception("O pagamento da compra informada ja foi realizado. - Operacao Cancelada.");
        } else {
            entidade.setStatusPagamento("Pago");
            entidade.setStatusEntrega("Em rota");
        }

        carrinhoRepository.save(entidade);

        return CarrinhoMapper.mapToDto(entidade);
    }


    public CarrinhoSaida entregarCompra(String idCliente, Long idCompra) throws Exception {

        CarrinhoEntity entidade = buscaNoRespositoryPorIdCompraEIdCliente(idCliente, idCompra);

        if (entidade.getStatusEntrega() == "Em rota") {
            entidade.setStatusEntrega("Entregue");
        } else if (entidade.getStatusPagamento() == "Pendente") {
            throw new Exception("Pagamento pendente.");
        } else {
            throw new Exception("A compra informada ja foi entregue.");
        }

        carrinhoRepository.save(entidade);

        return CarrinhoMapper.mapToDto(entidade);
    }

    public CarrinhoEntity buscaNoRespositoryPorIdCompraEIdCliente(String idCliente, Long idCompra) throws Exception {

        Optional<CarrinhoEntity> carrinhoEntity = carrinhoRepository.findById(idCompra);

        if (!carrinhoEntity.isPresent()) {
            throw new Exception("ID compra invalido.");
        }

        if (!carrinhoEntity.get().getIdCliente().equals(idCliente)) {
            throw new Exception("Id cliente invalido.");
        }

        return carrinhoEntity.get();
    }

}
