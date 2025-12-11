package br.gov.agu.pace.domain.pauta.mapper;

import br.gov.agu.pace.domain.enums.StatusEscalaPauta;
import br.gov.agu.pace.domain.orgaoJulgador.OrgaoJulgadorEntity;
import br.gov.agu.pace.domain.pauta.dtos.PautaDTO;
import br.gov.agu.pace.domain.pauta.entity.PautaEntity;
import br.gov.agu.pace.domain.sala.SalaEntity;
import org.springframework.stereotype.Component;

@Component
public class PautaMapper {

    public PautaEntity toEntity(PautaDTO dto, SalaEntity sala, OrgaoJulgadorEntity orgaoJulgador) {
        PautaEntity entity = new PautaEntity();
        entity.setData(dto.getData());
        entity.setTurno(dto.getTurno());
        entity.setSala(sala);
        entity.setOrgaoJulgador(orgaoJulgador);
        entity.setStatusEscalaAvaliador(StatusEscalaPauta.ESCALA_PENDENTE);
        entity.setStatusEscalaPautista(StatusEscalaPauta.ESCALA_PENDENTE);
        return entity;
    }
}
