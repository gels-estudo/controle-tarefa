package br.com.gels.seletivo.itau.api.domain.repository;

import br.com.gels.seletivo.itau.api.domain.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByLoginAndSenha(String email, String senha);

    Optional<Usuario> findByLogin(String login);
}
