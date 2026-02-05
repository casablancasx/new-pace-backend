package br.gov.agu.pace.domain.planilha.mapper;

import br.gov.agu.pace.domain.enums.ClasseJudicial;
import br.gov.agu.pace.domain.enums.Turno;
import br.gov.agu.pace.domain.enums.Uf;
import br.gov.agu.pace.domain.planilha.dtos.AudienciaDTO;
import org.apache.poi.ss.usermodel.Row;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

import static br.gov.agu.pace.domain.enums.Turno.MANHA;
import static br.gov.agu.pace.domain.enums.Turno.TARDE;

@Component
public class AudienciaRowMapper {

    private static final String POLO_PASSIVO = "INSTITUTO NACIONAL DO SEGURO SOCIAL - INSS";
    private static final String RURAL = "Rural";
    private static final String SALARIO_MATERNIDADE = "Salário-Maternidade (Art. 71/73)";


    public AudienciaDTO mapToAudienciaDTO(Row row) {
        AudienciaDTO audienciaDTO = new AudienciaDTO();
        audienciaDTO.setNumeroProcesso(getNumeroProcessoFromRow(row));
        audienciaDTO.setData(getDiaDataFromRow(row));
        audienciaDTO.setHora(getHoraFromRow(row));
        audienciaDTO.setAdvogados(getAdvogadosFromRow(row));
        audienciaDTO.setAssunto(getAssuntoFromRow(row));
        audienciaDTO.setOrgaoJulgador(getOrgaoJulgadorFromRow(row));
        audienciaDTO.setUf(getUfFromRow(audienciaDTO.getOrgaoJulgador()));
        audienciaDTO.setPoloAtivo(getPoloAtivoFromRow(row));
        audienciaDTO.setSala(getSalaFromRow(row));
        audienciaDTO.setTurno(getTurno(audienciaDTO.getHora()));
        audienciaDTO.setClasseJudicial(getClasseJudicialFromRow(row));
        return audienciaDTO;
    }


    private Turno getTurno(final String hora) {
        int hour = Integer.parseInt(hora.split(":")[0]);
        return hour < 13 ? MANHA : TARDE;
    }

    private String getHoraFromRow(Row row) {
        return row.getCell(0)
                .getStringCellValue()
                .split(" ")[1];
    }

    private LocalDate getDiaDataFromRow(Row row) {
        String dataString = row.getCell(0).getStringCellValue().split(" ")[0];
        return  LocalDate.parse(dataString, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    private String getNumeroProcessoFromRow(Row row) {
        return row.getCell(1).getStringCellValue();
    }

    private ClasseJudicial getClasseJudicialFromRow(Row row) {
        String classeJucialCelll = row.getCell(4).getStringCellValue();
        classeJucialCelll = classeJucialCelll.toUpperCase().trim();
        boolean isJudicial = classeJucialCelll.contains("JUIZADO ESPECIAL");

        if (isJudicial) return ClasseJudicial.JEF;
        else return ClasseJudicial.COMUM;
    }

    private String getOrgaoJulgadorFromRow(Row row) {
        return row.getCell(2).getStringCellValue();
    }

    private Uf getUfFromRow(String orgaoJulgador) {
        return Uf.valueOf(StringUtils.right(orgaoJulgador, 2).toUpperCase());
    }

    private String getPoloAtivoFromRow(Row row) {
        String texto = row.getCell(3).getStringCellValue();
        
        // Caso 1: Formato "NOME - CPF: xxx (AUTOR) X INSS..."
        if (texto.contains(" - CPF:")) {
            return texto.split(" - CPF:")[0].trim();
        }
        
        // Caso 2: Formato com quebra de linha "NOME\nINSS..."
        if (texto.contains("\n")) {
            String[] linhas = texto.split("\n");
            for (String linha : linhas) {
                linha = linha.trim();
                if (!linha.isEmpty() && !linha.contains(POLO_PASSIVO)) {
                    return linha;
                }
            }
        }
        
        // Fallback: remove o INSS e retorna o que sobrar
        return texto.replace(POLO_PASSIVO, "").trim();
    }

    private List<String> getAdvogadosFromRow(Row row) {
        return Arrays.stream(row.getCell(5).getStringCellValue().split(",")).toList();
    }

    private String getAssuntoFromRow(Row row) {
        //Lógica criada pois na planilha, as audiencias que possuem assunto "Rural"
        // sem ser "Aposentadoria Rural (Art. 48/51)" são na verdade Salario Maternidade
        String assunto = row.getCell(6).getStringCellValue();
        if (assunto.equals(RURAL.trim())) {
            return SALARIO_MATERNIDADE;
        }
        return assunto;
    }

    private String getSalaFromRow(Row row) {
        return row.getCell(8).getStringCellValue();
    }




}
