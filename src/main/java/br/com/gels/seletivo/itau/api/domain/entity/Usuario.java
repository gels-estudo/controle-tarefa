package br.com.gels.seletivo.itau.api.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Base64;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "usuario")
public class Usuario implements Serializable {
    private static final long serialVersionUID = 1L;
    @Column(name = "superusuario")
    boolean admin;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "increment")
    @Column
    private Long id;
    @Column(unique = true)
    @NotNull(message = "{validation.mail.notNull}")
    @NotEmpty(message = "{validation.mail.notEmpty}")
    private String login;
    @Column
    @NotEmpty(message = "{validation.password.notEmpty}")
    private String senha;

    public String getSenha() {
        return new String(Base64.getDecoder().decode(this.senha.getBytes()));
    }

    public void setSenha(String senha) {
        this.senha = new String(Base64.getEncoder().encode(senha.getBytes()));
    }
}
