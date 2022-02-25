package br.com.gels.seletivo.itau.api.service;

import br.com.gels.seletivo.itau.api.domain.entity.Usuario;
import br.com.gels.seletivo.itau.api.exception.APIException;

import javax.servlet.http.HttpServletRequest;

public interface UsuarioService {

    Usuario geUsuario(String login, String senha) throws APIException;

    Usuario loginUsuario(HttpServletRequest request) throws APIException;


}