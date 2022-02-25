package br.com.gels.seletivo.itau.api.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.Instant;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "tarefa")
public class Tarefa implements java.io.Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "increment")
    private Long id;

    @ManyToOne
    @JoinColumn(nullable = false, updatable = false, insertable = true)
    private Usuario usuario;

    @Column
    private String resumo;

    @Column
    private String descricao;

    @Column
    private String status;

    @Column
    private LocalDateTime dataCadastro;

    @Column
    private LocalDateTime dataAtualizacao;
}