package br.com.gels.seletivo.itau.api.service.impl;

import br.com.gels.seletivo.itau.api.adapter.TarefaAdapter;
import br.com.gels.seletivo.itau.api.adapter.dto.TarefaDTO;
import br.com.gels.seletivo.itau.api.domain.entity.Tarefa;
import br.com.gels.seletivo.itau.api.domain.entity.Usuario;
import br.com.gels.seletivo.itau.api.domain.repository.TarefaRepository;
import br.com.gels.seletivo.itau.api.exception.APIException;
import br.com.gels.seletivo.itau.api.service.TarefaService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class TarefaServiceImpl implements TarefaService {

    @Autowired
    private TarefaRepository tarefaRepository;

    @Autowired
    private MessageSource messageSource;

    @Override
    public List<TarefaDTO> consultarTarefa(Usuario usuario, String status) throws APIException {
        Tarefa tarefa = Tarefa.builder().build();
        if (status != null && !status.isEmpty())
            tarefa.setStatus(status);
        if (!usuario.isAdmin())
            tarefa.setUsuario(Usuario.builder().id(usuario.getId()).build());

        return TarefaAdapter.ENTITY_LIST_TO_DTO_LIST.apply( (tarefa.getUsuario() != null || tarefa.getStatus() != null)
                ? tarefaRepository.findAll(Example.of(tarefa), Sort.by("status").descending()) : tarefaRepository.findAllByOrderByStatusDesc());
    }

    @Override
    public TarefaDTO novaTarefa(Usuario usuario, TarefaDTO tarefa) throws APIException {
        Tarefa entity = Tarefa.builder().build();
        BeanUtils.copyProperties(tarefa, entity);
        entity.setId(null);
        entity.setUsuario(usuario);
        entity = tarefaRepository.save(entity);

        return TarefaAdapter.ENTITY_TO_DTO.apply(entity);
    }

    @Override
    public TarefaDTO atualizarTarefa(Usuario usuario, @Valid Long id, @Valid TarefaDTO tarefa) {
        Tarefa entity = getTarefaPorId(usuario, id);
        entity.setDataAtualizacao(LocalDateTime.now());
        return TarefaAdapter.ENTITY_TO_DTO.apply(entity);
    }

    @Override
    public void apagarTarefa(Usuario usuario, @Valid Long id) throws APIException {
        getTarefaPorId(usuario, id);
        tarefaRepository.deleteById(id);
    }

    private Tarefa getTarefaPorId(Usuario usuario, @Valid Long id) {
        Tarefa entity = tarefaRepository.findById(id)
                .orElseThrow(() -> new APIException(messageSource.getMessage("tarefa.nao-encontrada",
                        null, LocaleContextHolder.getLocale()), HttpStatus.BAD_REQUEST));

        if (entity.getUsuario().getId() != usuario.getId() && !usuario.isAdmin()) {
            throw new APIException(messageSource.getMessage("tarefa.nao-encontrada",
                    null, LocaleContextHolder.getLocale()), HttpStatus.BAD_REQUEST);
        }
        return entity;
    }
}
