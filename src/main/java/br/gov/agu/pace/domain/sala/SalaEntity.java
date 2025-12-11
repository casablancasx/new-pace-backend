package br.gov.agu.pace.domain.sala;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tb_salas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SalaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sala_id")
    private Long salaId;

    private String nome;

    public SalaEntity(String nome) {
        this.nome = nome;
    }
}
