package br.gov.agu.pace.contestacao;

import br.gov.agu.pace.domain.enums.TipoContestacao;
import br.gov.agu.pace.integrations.client.SapiensClient;
import br.gov.agu.pace.domain.planilha.dtos.AudienciaDTO;
import lombok.RequiredArgsConstructor;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static br.gov.agu.pace.domain.enums.TipoContestacao.SEM_TIPO;

@Service
@RequiredArgsConstructor
public class ContestacaoService {


    private final SapiensClient sapiensClient;
    private static final Pattern TIPOS_PATTERN = Pattern.compile("\\bTIPO\\s*([1-5])\\b", Pattern.CASE_INSENSITIVE);


    public AudienciaDTO adicionarTipoContestacaoEProcessoId(AudienciaDTO audienciaDTO, String token) {
        Long processoId = sapiensClient.getProcessoIdPorNumeroProcosso(audienciaDTO.getNumeroProcesso(), token);
        String conteudoContestacao = obterConteudoContestacaoPorProcessoId(processoId, token);
        TipoContestacao tipoContestacao = extrairTipoContestacao(conteudoContestacao);
        System.out.println(audienciaDTO.getNumeroProcesso() + " - " + tipoContestacao);
        audienciaDTO.setTipoContestacao(tipoContestacao);
        audienciaDTO.setProcessoId(processoId);
        return audienciaDTO;
    }

    private TipoContestacao extrairTipoContestacao(String conteudoContestacao) {
        Matcher matcher = TIPOS_PATTERN.matcher(conteudoContestacao);

        if (matcher.find()) {
            String tipo = matcher.group().toUpperCase();
            if (tipo.contains(" ")){
                tipo = tipo.replaceAll(" ", "");
            }
            return TipoContestacao.valueOf(tipo);
        }

        return SEM_TIPO;
    }

    private String obterConteudoContestacaoPorProcessoId(Long processoId, String token){
        Long documentoContestacaoId = sapiensClient.getIdDocumentoContestaao(processoId, token);
        String arquivoConstestacaoBase64 = sapiensClient.obterArquivoBase64PorIdDocumento(documentoContestacaoId, token);
        byte[] bytesArquivo = Base64.getDecoder().decode(arquivoConstestacaoBase64);
        String inicioArquivo = new String(bytesArquivo, StandardCharsets.ISO_8859_1);

        if (inicioArquivo.startsWith("%PDF")) {
            return extrairTextoPdf(bytesArquivo);
        } else {
            return new String(bytesArquivo, StandardCharsets.UTF_8);
        }
    }

    private String extrairTextoPdf(byte[] bytesPdf) {
        try (PDDocument documento = PDDocument.load(bytesPdf)) {
            PDFTextStripper stripper = new PDFTextStripper();
            return stripper.getText(documento);
        } catch (IOException e) {
            throw new RuntimeException("Erro ao ler PDF: ", e);
        }

    }
}
