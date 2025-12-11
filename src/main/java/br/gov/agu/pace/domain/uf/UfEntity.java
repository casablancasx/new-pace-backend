package br.gov.agu.pace.domain.uf;

import br.gov.agu.pace.domain.enums.Uf;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "tb_ufs")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UfEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer ufId;

    @Enumerated(EnumType.STRING)
    private Uf sigla;
}