package br.com.gels.seletivo.itau.api.adapter.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UsuarioAutenticacaoDTO {
    private String token_login;
    private String password;
    private String client_user;
    private String client_key;
    private Long user_id;
    private String username;
    private Long exp;
    private Boolean admin;
}
