package br.com.mercadosallas.categorias.service;

import br.com.mercadosallas.categorias.dto.CategoriaAtualizacaoForm;
import br.com.mercadosallas.categorias.dto.CategoriaDto;
import br.com.mercadosallas.categorias.dto.CategoriaForm;
import br.com.mercadosallas.categorias.exception.CategoriaAlreadyExistsException;
import br.com.mercadosallas.categorias.exception.CategoriaNotFoundException;
import br.com.mercadosallas.categorias.mapper.CategoriaMapper;
import br.com.mercadosallas.categorias.model.CategoriaEntity;
import br.com.mercadosallas.categorias.repository.CategoriaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static br.com.mercadosallas.utils.StringUtils.isNotNullOrBlank;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    private static final Logger log = LoggerFactory.getLogger(CategoriaService.class);

    public CategoriaDto adicionarCategoria(CategoriaForm form) {
        log.info("Adicionando categoria: {}", form.getNome());

        validarSeCategoriaJaExiste(form.getNome());

        CategoriaEntity categoriaEntity = CategoriaMapper.mapToEntity(form);

        CategoriaEntity categoria = categoriaRepository.save(categoriaEntity);

        log.info("Categoria adicionada com sucesso.");

        return CategoriaMapper.mapToDto(categoria);
    }

    public List<CategoriaDto> listarCategorias() {

        log.info("Listando categorias cadastradas.");

        List<CategoriaEntity> categorias = categoriaRepository.findAll();

        if (categorias.isEmpty())
            throw new CategoriaNotFoundException("Nenhuma categoria cadastrada.");

        return CategoriaMapper.mapToListDto(categorias);
    }

    public CategoriaDto listarCategoriaPorId(Long idCategoria) {

        log.info("Obtendo categoria id {}.", idCategoria);

        CategoriaEntity categoriaEncontrada = buscarCategoriaPorId(idCategoria);

        return CategoriaMapper.mapToDto(categoriaEncontrada);
    }



    public CategoriaDto alterarCategoria(Long idCategoria, CategoriaAtualizacaoForm form) {

        log.info("Alterando categoria.");

        CategoriaEntity categoriaEntity = buscarCategoriaPorId(idCategoria);

        validarSeCategoriaJaExiste(form.getNome());

        if (isNotNullOrBlank(form.getNome()))
            categoriaEntity.setNomeCategoria(form.getNome());

        log.info("Categoria alterada com sucesso.");

        return CategoriaMapper.mapToDto(categoriaEntity);
    }

    public void deletarCategoria(Long idCategoria) {

        log.info("Deletando categoria {}", idCategoria);

        CategoriaEntity categoriaEntity = buscarCategoriaPorId(idCategoria);

//        categoriaRepository.delete(idCategoria); //TODO Verificar se delete funcionara apos relcionar a categoria com produtos
        categoriaRepository.delete(categoriaEntity);

        log.info("Categoria deltado com sucesso.");

    }

    private void validarSeCategoriaJaExiste(String nomeCategoria) {
        Optional<CategoriaEntity> categoriaOpt = categoriaRepository.findByNomeCategoria(nomeCategoria);
        if (categoriaOpt.isPresent())
            throw new CategoriaAlreadyExistsException("Categoria já cadastrada.");
    }

    public CategoriaEntity buscarCategoriaPorId(Long idCategoria) {
        Optional<CategoriaEntity> categoriaOpt = categoriaRepository.findById(idCategoria);
        return categoriaOpt.orElseThrow(() -> new CategoriaNotFoundException("Categoria não encontrada."));
    }

}

