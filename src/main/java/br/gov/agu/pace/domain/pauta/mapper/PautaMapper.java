package br.gov.agu.pace.domain.pauta.mapper;

import br.gov.agu.pace.domain.audiencia.mapper.AudienciaMapper;
import br.gov.agu.pace.domain.enums.Turno;
import br.gov.agu.pace.domain.orgaoJulgador.OrgaoJulgadorEntity;
import br.gov.agu.pace.domain.pauta.dtos.PautaDTO;
import br.gov.agu.pace.domain.pauta.dtos.PautaResponseDTO;
import br.gov.agu.pace.domain.pauta.entity.PautaEntity;
import br.gov.agu.pace.domain.sala.SalaEntity;
import br.gov.agu.pace.domain.user.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PautaMapper {

    private final AudienciaMapper audienciaMapper;

    public PautaEntity toEntity(PautaDTO dto, SalaEntity sala, OrgaoJulgadorEntity orgaoJulgador) {
        PautaEntity entity = new PautaEntity();
        entity.setData(dto.getData());
        entity.setTurno(Turno.fromString(dto.getTurno()));
        entity.setSala(sala);
        entity.setOrgaoJulgador(orgaoJulgador);
        return entity;
    }

    public PautaResponseDTO toResponseDto(PautaEntity pautaEntity, UserEntity usuarioSolicitante){
        PautaResponseDTO responseDto = new PautaResponseDTO();
        responseDto.setPautaId(pautaEntity.getPautaId());
        responseDto.setTurno(pautaEntity.getTurno());
        responseDto.setData(pautaEntity.getData());
        responseDto.setSala(pautaEntity.getSala().getNome());
        responseDto.setOrgaoJulgador(pautaEntity.getOrgaoJulgador().getNome());
        responseDto.setUf(pautaEntity.getOrgaoJulgador().getUf().getSigla());
        responseDto.setAudiencias(pautaEntity.getAudiencias().stream()
                .map(audiencia -> audienciaMapper.toResponseDTO(audiencia, usuarioSolicitante))
                .toList());
        return responseDto;
    }
}
