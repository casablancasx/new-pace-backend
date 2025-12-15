package br.gov.agu.pace.domain.avaliador;

import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class AvaliadorService {


    public AvaliadorEntity selecionarAvaliador(final List<AvaliadorEntity> avaliadores) {
        return avaliadores.stream()
                .min(Comparator.comparingInt(AvaliadorEntity::calcularCargaTrabalho))
                .orElseThrow(() -> new RuntimeException("Nenhum avaliador disponivel para escala"));
    }
}
