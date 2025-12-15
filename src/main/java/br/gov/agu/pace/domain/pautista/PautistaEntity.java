package br.gov.agu.pace.domain.pautista;

import br.gov.agu.pace.domain.user.SapiensUser;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "tb_pautistas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PautistaEntity extends SapiensUser {

    private String telefone;

}
