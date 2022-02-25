package br.com.gels.seletivo.itau.api.service;

import br.com.gels.seletivo.itau.api.adapter.dto.TarefaDTO;
import br.com.gels.seletivo.itau.api.domain.entity.Usuario;
import br.com.gels.seletivo.itau.api.exception.APIException;

import javax.validation.Valid;
import java.util.List;

public interface TarefaService {

    List<TarefaDTO> consultarTarefa(Usuario usuario, String status) throws APIException;

    TarefaDTO novaTarefa(Usuario usuario, TarefaDTO tarefa) throws APIException;

    TarefaDTO atualizarTarefa(Usuario usuario, @Valid Long id, @Valid TarefaDTO tarefa);

    void apagarTarefa(Usuario usuario, @Valid Long id) throws APIException;

}