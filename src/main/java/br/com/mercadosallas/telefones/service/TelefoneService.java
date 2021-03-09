package br.com.mercadosallas.telefones.service;

import br.com.mercadosallas.fornecedores.model.FornecedorEntity;
import br.com.mercadosallas.fornecedores.service.FornecedorService;
import br.com.mercadosallas.telefones.dto.TelefoneAtualizacaoForm;
import br.com.mercadosallas.telefones.dto.TelefoneDto;
import br.com.mercadosallas.telefones.dto.TelefoneForm;
import br.com.mercadosallas.telefones.exception.MinimoTelefoneException;
import br.com.mercadosallas.telefones.exception.TelefoneNotFoundException;
import br.com.mercadosallas.telefones.mapper.TelefoneMapper;
import br.com.mercadosallas.telefones.model.TelefoneEntity;
import br.com.mercadosallas.telefones.repository.TelefoneRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static br.com.mercadosallas.utils.StringUtils.isNotNullOrBlank;

@Service
public class TelefoneService {

    @Autowired
    private TelefoneRepository telefoneRepository;

    @Autowired
    private FornecedorService fornecedorService;

    private static final Logger log = LoggerFactory.getLogger(TelefoneService.class);


    public TelefoneDto adicionarTelefoneAoFornecedor(Long idFornecedor, TelefoneForm form) {

        log.info("Adicionando telefone ao fornecedor: {}", idFornecedor);

        fornecedorService.buscarFornecedorPorId(idFornecedor);

        TelefoneEntity telefoneEntity = TelefoneMapper.mapToEntity(form);
        telefoneEntity.setIdFornecedor(idFornecedor);

        TelefoneEntity telefone = telefoneRepository.save(telefoneEntity);

        log.info("Telefone adicionado com sucesso.");

        return TelefoneMapper.mapToDto(telefone);

    }

    public List<TelefoneDto> listarTelefonesDoFornecedor(Long idFornecedor) {

        log.info("Listando todos telefones cadastrados do fornecedor.");

        List<TelefoneEntity> telefonesDoFornecedor = fornecedorService.buscarTodosTelefonesDoFornecedor(idFornecedor);

        if (telefonesDoFornecedor.isEmpty())
            throw new TelefoneNotFoundException("Nenhum telefone cadastrado.");

        return TelefoneMapper.mapToListDto(telefonesDoFornecedor);
    }

    public TelefoneDto listarTelefonePorId(Long idFornecedor, Long idTelefone) {

        log.info("Listando telefone do fornecedor pelo id.");

        FornecedorEntity fornecedorEntity = fornecedorService.buscarFornecedorPorId(idFornecedor);

        TelefoneEntity telefoneEntity = filtrarTelefonesPorId(idTelefone, fornecedorEntity.getTelefones());

        return TelefoneMapper.mapToDto(telefoneEntity);
    }

    public TelefoneDto alterarTelefone(Long idFornecedor, Long idTelefone, TelefoneAtualizacaoForm form) {

        log.info("Alterando telefone do fornecedor.");

        FornecedorEntity fornecedorEncontrado = fornecedorService.buscarFornecedorPorId(idFornecedor);

        TelefoneEntity telefoneEntity = filtrarTelefonesPorId(idTelefone, fornecedorEncontrado.getTelefones());

        if (isNotNullOrBlank(form.getDdd()))
            telefoneEntity.setDdd(form.getDdd());

        if (isNotNullOrBlank(form.getNumero()))
            telefoneEntity.setNumero(form.getNumero());

        if (isNotNullOrBlank(form.getTipo()))
            telefoneEntity.setTipo(form.getTipo());

        log.info("Telefone alterado com sucesso.");

        return TelefoneMapper.mapToDto(telefoneEntity);
    }

    public void deletarTelefone(Long idFornecedor, Long idTelefone) {

        log.info("Deletando telefone {} do fornecedor {}", idFornecedor, idTelefone);

        FornecedorEntity fornecedorEncontrado = fornecedorService.buscarFornecedorPorId(idFornecedor);

        TelefoneEntity telefoneEntity = filtrarTelefonesPorId(idTelefone, fornecedorEncontrado.getTelefones());

        if (fornecedorEncontrado.getTelefones().size() <= 1)
            throw new MinimoTelefoneException("Não é possível deletar telefone. É necessário ter um ou mais telefones cadastrados.");

        telefoneRepository.delete(telefoneEntity.getId());

        log.info("Telefone deltado com sucesso.");

    }

    private TelefoneEntity filtrarTelefonesPorId(Long idTelefone, List<TelefoneEntity> telefones) {
        return telefones.stream().filter(
                t -> t.getId().equals(idTelefone)).findFirst()
                .orElseThrow(
                        () -> new TelefoneNotFoundException(String.format("Telefone não encontrado para o id %s", idTelefone)));
    }

}

