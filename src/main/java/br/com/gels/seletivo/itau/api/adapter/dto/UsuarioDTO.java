package br.com.gels.seletivo.itau.api.adapter.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsuarioDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String login;

}