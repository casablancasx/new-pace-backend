package br.gov.agu.pace.domain.assunto;

import br.gov.agu.pace.domain.audiencia.entity.AudienciaEntity;
import br.gov.agu.pace.planilha.dtos.AudienciaDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tb_assuntos")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AssuntoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long assuntoId;

    private String nome;


}
