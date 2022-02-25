package br.com.gels.seletivo.itau.api.adapter.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class ErroResponseDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String statusCode;

    private String message;

    private String cause;
}