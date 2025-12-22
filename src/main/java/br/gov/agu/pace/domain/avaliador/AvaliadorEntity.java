package br.gov.agu.pace.domain.avaliador;

import br.gov.agu.pace.domain.audiencia.entity.AudienciaEntity;
import br.gov.agu.pace.domain.enums.RespostaAnaliseAvaliador;
import br.gov.agu.pace.domain.pauta.entity.PautaEntity;
import br.gov.agu.pace.domain.user.SapiensUser;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tb_avaliadores")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AvaliadorEntity extends SapiensUser {

    private String telefone;

    private Integer quantidadePautas = 0;

    private Integer quantidadeAudiencias = 0;

    private boolean disponivel = true;

    @OneToMany(mappedBy = "avaliador")
    private List<AudienciaEntity> audiencias = new ArrayList<>();


    public int calcularCargaTrabalho() {
        final int PESO_PAUTA = 1;
        final int PESO_AUDIENCIA = 2;
        return (quantidadePautas * PESO_PAUTA) + (quantidadeAudiencias * PESO_AUDIENCIA);
    }

    public void incrementarPautas() {
        this.quantidadePautas++;
    }

    public void incrementarAudiencias(List<AudienciaEntity> audiencias) {
        quantidadeAudiencias += audiencias.size();
    }

    public Long getQuantidaDeAudienciasAvaliadas(){
        return audiencias.stream()
                .filter(a -> a.getAnaliseAvaliador() != RespostaAnaliseAvaliador.ANALISE_PENDENTE)
                .count();
    }

    public Long getQuantidadeTotalAudiencias(){
        return (long) audiencias.size();
    }

}
