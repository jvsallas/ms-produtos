package br.com.mercadosallas.produtos.service;

import br.com.mercadosallas.categorias.model.CategoriaEntity;
import br.com.mercadosallas.categorias.service.CategoriaService;
import br.com.mercadosallas.fornecedores.exception.ProdutoNotFoundException;
import br.com.mercadosallas.fornecedores.model.FornecedorEntity;
import br.com.mercadosallas.fornecedores.service.FornecedorService;
import br.com.mercadosallas.produtos.dto.ProdutoAtualizacaoForm;
import br.com.mercadosallas.produtos.dto.ProdutoDto;
import br.com.mercadosallas.produtos.dto.ProdutoForm;
import br.com.mercadosallas.produtos.exception.exceptions.ProdutoAlreadyExistsException;
import br.com.mercadosallas.produtos.mapper.ProdutoMapper;
import br.com.mercadosallas.produtos.model.ProdutoEntity;
import br.com.mercadosallas.produtos.repository.ProdutoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static br.com.mercadosallas.utils.StringUtils.isNotNullOrBlank;

@Service
public class ProdutoService {

    private static final Logger log = LoggerFactory.getLogger(ProdutoService.class);

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private FornecedorService fornecedorService;

    @Autowired
    private CategoriaService categoriaService;

    public ProdutoDto adicionarProduto(ProdutoForm produtoForm) {

        log.info("Validando registro de produto.");

        validarProdutoJaCadastrado(produtoForm.getNome());

        ProdutoEntity produtoEntity = ProdutoMapper.mapToEntity(produtoForm);

        List<FornecedorEntity> fornecedorEntities = fornecedorService.buscarFornecedores(produtoForm.getFornecedores());
        CategoriaEntity categoriaEntity = categoriaService.buscarCategoriaPorId(produtoForm.getCategoria());

        produtoEntity.setFornecedores(fornecedorEntities);
        produtoEntity.setCategoria(categoriaEntity);

        ProdutoEntity entity = produtoRepository.save(produtoEntity);

        log.info("Produto cadastrado com sucesso na base de dados.");

        return ProdutoMapper.mapToDto(entity);
    }

    public List<ProdutoDto> listarProdutos() {

        log.info("Listando todos produtos.");

        List<ProdutoEntity> produtos = produtoRepository.findAll();

        if (produtos.isEmpty())
            throw new ProdutoNotFoundException("Nenhum produto encontrado;");

        return ProdutoMapper.mapToListDto(produtos);
    }

    public ProdutoDto listarProdutoPorId(String id) {

        ProdutoEntity produtoEntity = buscarProdutoPorId(id);

        return ProdutoMapper.mapToDto(produtoEntity);
    }

    public ProdutoDto alterarDadosProdutos(String id, ProdutoAtualizacaoForm form) {

        log.info("Alterando dados do produto.");

        ProdutoEntity produtoEntity = buscarProdutoPorId(id);

        if (isNotNullOrBlank(form.getNome()))
            produtoEntity.setNome(form.getNome());

        if (isNotNullOrBlank(form.getDescricao()))
            produtoEntity.setDescricao(form.getDescricao());

        if (Objects.nonNull(form.getPreco()))
            produtoEntity.setPreco(form.getPreco());

        return ProdutoMapper.mapToDto(produtoEntity);
    }

    public void deletarProduto(String id) {

        log.info("Deletando produto: {}", id);

        buscarProdutoPorId(id);

        produtoRepository.deleteById(id);
    }

    public ProdutoEntity buscarProdutoPorId(String id) {
        Optional<ProdutoEntity> produtoOpt = produtoRepository.findById(id);

        return produtoOpt.orElseThrow(() ->
                new ProdutoNotFoundException(String.format("Produto não encontrado para o id %s", id)));
    }

    private void validarProdutoJaCadastrado(String nome) {
        Optional<ProdutoEntity> produtoOpt = produtoRepository.findByNome(nome);

        if (produtoOpt.isPresent())
            throw new ProdutoAlreadyExistsException("Produto informado já cadastrado.");
    }


}

