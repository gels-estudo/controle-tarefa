package br.com.gels.seletivo.itau.api.domain.repository;

import br.com.gels.seletivo.itau.api.domain.entity.Tarefa;
import br.com.gels.seletivo.itau.api.domain.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TarefaRepository extends JpaRepository<Tarefa, Long> {

    List<Tarefa> findAllByOrderByStatusDesc();

    List<Tarefa> findByStatusAndUsuario(String status, Usuario usuario);
}
