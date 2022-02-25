package br.com.gels.seletivo.itau.api.adapter;

import br.com.gels.seletivo.itau.api.adapter.dto.TarefaDTO;
import br.com.gels.seletivo.itau.api.adapter.dto.UsuarioDTO;
import br.com.gels.seletivo.itau.api.domain.entity.Tarefa;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class TarefaAdapter {

    public static final Function<TarefaDTO, Tarefa> DTO_TO_ENTITY = (dto -> {
        Tarefa tarefa = new Tarefa();
        BeanUtils.copyProperties(dto, tarefa);
        return tarefa;
    });

    public static final Function<Tarefa, TarefaDTO> ENTITY_TO_DTO = (entity -> {
        TarefaDTO tarefaDTO = TarefaDTO.builder().build();
        UsuarioDTO usuarioDTO = UsuarioDTO.builder().build();
        BeanUtils.copyProperties(entity.getUsuario(), usuarioDTO);
        BeanUtils.copyProperties(entity, tarefaDTO);
        tarefaDTO.setUsuario(usuarioDTO);
        return tarefaDTO;
    });

    public static final Function<List<Tarefa>, List<TarefaDTO>> ENTITY_LIST_TO_DTO_LIST = (entity -> {
        List<TarefaDTO> dtoList = new ArrayList<>();
        entity.forEach(tarefa -> {
            TarefaDTO tarefaDTO = TarefaDTO.builder().build();
            UsuarioDTO usuarioDTO = UsuarioDTO.builder().build();
            BeanUtils.copyProperties(tarefa, tarefaDTO);
            BeanUtils.copyProperties(tarefa.getUsuario(), usuarioDTO);
            tarefaDTO.setUsuario(usuarioDTO);
            dtoList.add(tarefaDTO);
        });
        return dtoList;
    });
}
