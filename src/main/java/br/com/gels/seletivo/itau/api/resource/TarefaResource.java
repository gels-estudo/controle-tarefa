package br.com.gels.seletivo.itau.api.resource;

import br.com.gels.seletivo.itau.api.adapter.dto.TarefaDTO;
import br.com.gels.seletivo.itau.api.domain.TarefaDomain;
import br.com.gels.seletivo.itau.api.exception.APIException;
import br.com.gels.seletivo.itau.api.util.Constants;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "todo-list", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@CrossOrigin(maxAge = 3600, origins = "*", allowedHeaders = {"Authorization","Content-Type","Accept","token"})
public class TarefaResource {
	
	private final TarefaDomain domain;
	
	@GetMapping
	@ApiOperation(value = Constants.ENDPOINT_GET_CONSULTAR_TAREFAS)
	public ResponseEntity<List<TarefaDTO>> consultarTarefa(
			@RequestParam(value = "status", required = false) String status, HttpServletRequest request) throws APIException {
		return new ResponseEntity<>(domain.consultarTarefa(request, status), HttpStatus.OK);
	}
	
	@PutMapping(path="/{id}")
	@ApiOperation(value = Constants.ENDPOINT_PUT_ATUALIZAR_TAREFA)
	public ResponseEntity<TarefaDTO> atualizarTarefa(
			@Valid @PathVariable(value = "id") Long id, 
			@Valid @RequestBody TarefaDTO tarefa, HttpServletRequest request) throws APIException {
		return new ResponseEntity<>(domain.atualizarTarefa(request, id, tarefa), HttpStatus.OK);
	}
	
	@PostMapping
	@ApiOperation(value = Constants.ENDPOINT_POST_INCLUIR_TAREFA)
	public ResponseEntity<TarefaDTO> novaTarefa(
			@Valid @RequestBody TarefaDTO tarefa, HttpServletRequest request) throws APIException {
		return new ResponseEntity<>(domain.incluirTarefa(request, tarefa), HttpStatus.CREATED);
	}
	
	@DeleteMapping(path="/{id}")
	@ApiOperation(value = Constants.ENDPOINT_EXCLUIR_TAREFA)
	public ResponseEntity<?> apagarTarefa(@Valid @PathVariable(value = "id") Long id,
			HttpServletRequest request) throws APIException {
		domain.excluirTarefa(request, id);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}

}