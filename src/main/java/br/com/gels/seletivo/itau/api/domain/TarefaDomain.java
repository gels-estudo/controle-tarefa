package br.com.gels.seletivo.itau.api.domain;

import br.com.gels.seletivo.itau.api.adapter.dto.TarefaDTO;
import br.com.gels.seletivo.itau.api.exception.APIException;
import br.com.gels.seletivo.itau.api.service.TarefaService;
import br.com.gels.seletivo.itau.api.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
public class TarefaDomain {

    @Autowired
    private TarefaService tarefaService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private MessageSource messageSource;


    public List<TarefaDTO> consultarTarefa(HttpServletRequest request, String status) {
        return tarefaService.consultarTarefa(this.usuarioService.loginUsuario(request), status);
    }

    public TarefaDTO incluirTarefa(HttpServletRequest request, TarefaDTO tarefa) {
        if (!tarefa.getStatus().equalsIgnoreCase("peding") &&
                !tarefa.getStatus().equalsIgnoreCase("completed"))
            throw new APIException(messageSource.getMessage("status.invalido",
                    null, LocaleContextHolder.getLocale()));

        return tarefaService.novaTarefa(this.usuarioService.loginUsuario(request), tarefa);
    }

    public TarefaDTO atualizarTarefa(HttpServletRequest request, Long id, TarefaDTO tarefa) {
        if (!tarefa.getStatus().equalsIgnoreCase("peding") &&
                !tarefa.getStatus().equalsIgnoreCase("completed"))
            throw new APIException(messageSource.getMessage("status.invalido",
                    null, LocaleContextHolder.getLocale()));

        return tarefaService.atualizarTarefa(this.usuarioService.loginUsuario(request), id, tarefa);
    }

    public void excluirTarefa(HttpServletRequest request, Long id) throws APIException {
        tarefaService.apagarTarefa(this.usuarioService.loginUsuario(request), id);
    }
}