package br.com.gels.seletivo.itau.api.service.impl;

import br.com.gels.seletivo.itau.api.adapter.dto.UsuarioAutenticacaoDTO;
import br.com.gels.seletivo.itau.api.auth.AuthenticationValidation;
import br.com.gels.seletivo.itau.api.domain.entity.Usuario;
import br.com.gels.seletivo.itau.api.domain.repository.UsuarioRepository;
import br.com.gels.seletivo.itau.api.exception.APIException;
import br.com.gels.seletivo.itau.api.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;


@Service
@Transactional
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private AuthenticationValidation authenticationValidation;

    @Override
    public Usuario geUsuario(String login, String senha) throws APIException {
        return usuarioRepository.findByLoginAndSenha(login, senha)
                .orElseThrow(() -> new APIException(messageSource.getMessage("usuario.nao-encontrado",
                        null, LocaleContextHolder.getLocale()), HttpStatus.UNAUTHORIZED));
    }


    @Override
    public Usuario loginUsuario(HttpServletRequest request) throws APIException {
        UsuarioAutenticacaoDTO usuarioAutenticacaoDTO = authenticationValidation.validarToken(request);
        return usuarioRepository.findByLogin(usuarioAutenticacaoDTO.getUsername()).orElseThrow(
                () -> new APIException(messageSource.getMessage("usuario.nao-encontrado",
                        null, LocaleContextHolder.getLocale()), HttpStatus.UNAUTHORIZED));
    }
}