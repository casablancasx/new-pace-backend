package br.gov.agu.pace.domain.avaliador;

import br.gov.agu.pace.domain.audiencia.entity.AudienciaEntity;
import br.gov.agu.pace.domain.enums.RespostaAnaliseAvaliador;
import br.gov.agu.pace.domain.pauta.entity.PautaEntity;
import br.gov.agu.pace.domain.user.SapiensUser;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
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

    private Long quantidadePautas = 0L;

    private Long quantidadeAudiencias = 0L;

    private Long quantidadeAudienciasAvaliadas = 0L;

    private boolean disponivel = true;

    @JsonIgnore
    @OneToMany(mappedBy = "avaliador", fetch = FetchType.EAGER)
    private List<AudienciaEntity> audiencias = new ArrayList<>();


    @JsonIgnore
    public Long calcularCargaTrabalho() {
        final int PESO_PAUTA = 1;
        final int PESO_AUDIENCIA = 2;
        return (quantidadePautas * PESO_PAUTA) + (quantidadeAudiencias * PESO_AUDIENCIA);
    }

    public void incrementarPautas() {
        this.quantidadePautas++;
    }

    @JsonIgnore
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

    public void incrementarQuantidadeAudienciasAnalisadas() {
        quantidadeAudienciasAvaliadas++;
    }
}
