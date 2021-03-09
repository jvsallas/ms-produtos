package br.com.mercadosallas.fornecedores.service;

import br.com.mercadosallas.fornecedores.dto.FornecedorAtualizacaoForm;
import br.com.mercadosallas.fornecedores.dto.FornecedorDto;
import br.com.mercadosallas.fornecedores.dto.FornecedorForm;
import br.com.mercadosallas.fornecedores.exception.FornecedorAlreadyExistsException;
import br.com.mercadosallas.fornecedores.exception.FornecedorNotFoundException;
import br.com.mercadosallas.fornecedores.mapper.FornecedorMapper;
import br.com.mercadosallas.fornecedores.model.FornecedorEntity;
import br.com.mercadosallas.fornecedores.repository.FornecedorRepository;
import br.com.mercadosallas.telefones.model.TelefoneEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static br.com.mercadosallas.utils.StringUtils.isNotNullOrBlank;

@Service
public class FornecedorService {

    @Autowired
    private FornecedorRepository fornecedorRepository;

    private static final Logger log = LoggerFactory.getLogger(FornecedorService.class);

    public FornecedorDto adicionarFornecedor(FornecedorForm form) {
        log.info("Adicionando fornecedor: {}", form.getNome());

        validarSeFornecedorJaExistePorNome(form.getNome());

        FornecedorEntity fornecedorEntity = FornecedorMapper.mapToEntity(form);

        FornecedorEntity fornecedor = fornecedorRepository.save(fornecedorEntity);

        log.info("Fornecedor adicionado com sucesso.");

        return FornecedorMapper.mapToDto(fornecedor);
    }

    public List<FornecedorDto> listarFornecedores() {

        log.info("Listando fornecedores cadastrados.");

        List<FornecedorEntity> categorias = fornecedorRepository.findAll();

        if (categorias.isEmpty())
            throw new FornecedorNotFoundException("Nenhum fornecedor cadastrado.");

        return FornecedorMapper.mapToListDto(categorias);
    }

    public FornecedorDto buscarFornecedor(Long idFornecedor) {

        log.info("Obtendo fornecedor id {}.", idFornecedor);

        FornecedorEntity fornecedorEncontrado = buscarFornecedorPorId(idFornecedor);

        return FornecedorMapper.mapToDto(fornecedorEncontrado);
    }

    public List<TelefoneEntity> buscarTodosTelefonesDoFornecedor(Long idFornecedor) {

        log.info("Obtendo telefones do fornecedor id {}.", idFornecedor);

        FornecedorEntity fornecedor = buscarFornecedorPorId(idFornecedor);

        return fornecedor.getTelefones();
    }

    public FornecedorDto alterarFornecedor(Long idFornecedor, FornecedorAtualizacaoForm form) {

        log.info("Alterando fornecedor.");

        FornecedorEntity fornecedorEntity = buscarFornecedorPorId(idFornecedor);

        if (isNotNullOrBlank(form.getNome())) {
            validarSeFornecedorJaExistePorNome(form.getNome());
            fornecedorEntity.setNome(form.getNome());
        }

        if (form.getCnpj() != null){
            validarSeFornecedorJaExistePorCnpj(form.getCnpj());
            fornecedorEntity.setCnpj(form.getCnpj());
        }

        if (isNotNullOrBlank(form.getEmail()))
            fornecedorEntity.setEmail(form.getEmail());

        log.info("Fornecedor alterado com sucesso.");

        return FornecedorMapper.mapToDto(fornecedorEntity);
    }

    public void deletarFornecedor(Long idFornecedor) {

        log.info("Deletando fornecedor {}", idFornecedor);

        FornecedorEntity fornecedorEntity = buscarFornecedorPorId(idFornecedor);

//        categoriaRepository.delete(idFornecedor); //TODO Verificar se delete funcionara apos relcionar a categoria com produtos
        fornecedorRepository.delete(fornecedorEntity);

        log.info("Fornecedor deletado com sucesso.");

    }

    private void validarSeFornecedorJaExistePorNome(String nomeFornecedor) {
        Optional<FornecedorEntity> fornecedorFromNomeOpt = fornecedorRepository.findByNome(nomeFornecedor);
        if (fornecedorFromNomeOpt.isPresent())
            throw new FornecedorAlreadyExistsException("Fornecedor já cadastrado.");
    }

    private void validarSeFornecedorJaExistePorCnpj(String cnpj) {
        Optional<FornecedorEntity> fornecedorFromCNPJOpt = fornecedorRepository.findByCnpj(cnpj);
        if (fornecedorFromCNPJOpt.isPresent())
            throw new FornecedorAlreadyExistsException("CNPJ do fornecedor já foi cadastrado.");
    }

    public FornecedorEntity buscarFornecedorPorId(Long idCategoria) {
        Optional<FornecedorEntity> categoriaOpt = fornecedorRepository.findById(idCategoria);
        return categoriaOpt.orElseThrow(() -> new FornecedorNotFoundException("Fornecedor não encontrado."));
    }

    public List<FornecedorEntity> buscarFornecedores(List<Long> idsFornecedores){
        List<FornecedorEntity> fornecedores = fornecedorRepository.findAllById(idsFornecedores);
        if (fornecedores.isEmpty())
            throw new FornecedorNotFoundException("Não foi encontrado nenhum fornecedor para o(s) id(s) informado(s).");

        return fornecedores;
    }

}

