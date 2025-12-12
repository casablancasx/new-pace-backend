package br.gov.agu.pace.domain.planilha.service;

import br.gov.agu.pace.domain.planilha.dtos.AudienciaDTO;

import br.gov.agu.pace.domain.planilha.mapper.AudienciaRowMapper;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ExcelReaderService {

    private final AudienciaRowMapper audienciaRowMapper;


    public Set<AudienciaDTO> importarPlanilha(MultipartFile file) throws IOException, Exception {

        Set<AudienciaDTO> audiencias = new HashSet<>();
        try (InputStream inputStream = file.getInputStream()){

            Workbook workbook = WorkbookFactory.create(inputStream);
            Sheet sheet = workbook.getSheetAt(0);

            for (Row row : sheet) {
                if (row == null || row.getRowNum() == 0) continue;
                if (row.getCell(0) == null || row.getCell(0).getStringCellValue().equals("")) break;

                var audienciaDTO = audienciaRowMapper.mapToAudienciaDTO(row);

                audiencias.add(audienciaDTO);
            }
        }

        return audiencias;
    }
}
