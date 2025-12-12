package br.gov.agu.pace.domain.assunto;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
