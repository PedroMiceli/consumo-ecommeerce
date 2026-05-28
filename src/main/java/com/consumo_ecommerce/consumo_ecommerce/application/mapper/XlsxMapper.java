package com.consumo_ecommerce.consumo_ecommerce.application.mapper;

import com.consumo_ecommerce.consumo_ecommerce.application.dtos.venda.VendaRequest;
import com.consumo_ecommerce.consumo_ecommerce.exceptions.CampoObrigatorioException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Component
public class XlsxMapper {

    public static List<VendaRequest> converterParaVendaRequest(MultipartFile arquivo) {
        try (InputStream inputStream = arquivo.getInputStream();
             Workbook workbook = WorkbookFactory.create(inputStream)) {

            Sheet sheet = workbook.getSheetAt(0);

            List<VendaRequest> vendas = new ArrayList<>();

            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row linha = sheet.getRow(i);

                if (linha == null) {
                    continue;
                }

                VendaRequest request = new VendaRequest(
                        null,
                        getString(linha.getCell(0)),              // numeroVenda
                        getLocalDateTime(linha.getCell(1)),       // dataVenda
                        getString(linha.getCell(2)),              // deposito
                        getString(linha.getCell(3)),              // estado
                        getString(linha.getCell(4)),              // descricaoStatus
                        getBooleanSimNao(linha.getCell(5)),       // pacoteDiversosProdutos
                        getBooleanSimNao(linha.getCell(6)),       // pertenceKit
                        getInteger(linha.getCell(7)),             // unidades
                        getBigDecimal(linha.getCell(8)),          // receitaProdutos
                        getBigDecimal(linha.getCell(9)),          // receitaAcrescimoPreco
                        getBigDecimal(linha.getCell(10)),         // taxaParcelamentoAcrescimo
                        getBigDecimal(linha.getCell(11)),         // tarifaVendaImpostos
                        getBigDecimal(linha.getCell(12)),         // receitaEnvio
                        getBigDecimal(linha.getCell(13)),         // tarifasEnvio
                        getBigDecimal(linha.getCell(14)),         // custoEnvioMedidasPeso
                        getBigDecimal(linha.getCell(15)),         // custoDiferencasMedidasPeso
                        getBigDecimal(linha.getCell(16)),         // descontosBonus
                        getBigDecimal(linha.getCell(17)),         // cancelamentosReembolsos
                        getBigDecimal(linha.getCell(18)),         // total
                        getString(linha.getCell(19)),             // mesFaturamentoTarifas
                        getString(linha.getCell(20)),             // pedidoCompra
                        false,                                    // vendaPorPublicidade
                        getString(linha.getCell(21))              // numeroAnuncio
                );

                vendas.add(request);
            }

            return vendas;

        } catch (Exception ex) {
            throw new RuntimeException("Erro ao processar arquivo XLSX: " + ex.getMessage(), ex);
        }
    }

    private String getString(Cell cell) {
        if (cell == null) {
            return null;
        }

        DataFormatter formatter = new DataFormatter();
        String valor = formatter.formatCellValue(cell);

        return valor == null || valor.isBlank() ? null : valor.trim();
    }

    private BigDecimal getBigDecimal(Cell cell) {
        String valor = getString(cell);

        if (valor == null) {
            return null;
        }

        valor = valor
                .replace("R$", "")
                .replace(".", "")
                .replace(",", ".")
                .trim();

        if (valor.isBlank()) {
            return null;
        }

        return new BigDecimal(valor);
    }

    private Integer getInteger(Cell cell) {
        String valor = getString(cell);

        if (valor == null) {
            return null;
        }

        valor = valor.replace(".", "").replace(",", ".").trim();

        return new BigDecimal(valor).intValue();
    }

    private Boolean getBooleanSimNao(Cell cell) {
        String valor = getString(cell);

        if (valor == null) {
            return null;
        }

        return valor.equalsIgnoreCase("Sim")
                || valor.equalsIgnoreCase("S")
                || valor.equalsIgnoreCase("True")
                || valor.equalsIgnoreCase("Verdadeiro");
    }

    private LocalDateTime getLocalDateTime(Cell cell) {
        if (cell == null) {
            return null;
        }

        if (cell.getCellType() == CellType.NUMERIC && DateUtil.isCellDateFormatted(cell)) {
            return cell.getLocalDateTimeCellValue();
        }

        String valor = getString(cell);

        if (valor == null) {
            return null;
        }

        // Exemplo vindo da planilha:
        // 30 de abril de 2026 22:27 hs.
        // Define a formatter for the specific date string format
        DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                .appendPattern("dd 'de' MMMM 'de' yyyy HH:mm 'hs.'")
                .toFormatter(new Locale("pt", "BR")); // Specify Portuguese locale for month names

        try {
            return LocalDateTime.parse(valor, formatter);
        } catch (DateTimeParseException e) {
            throw new CampoObrigatorioException("Formato de data inválido para: " + valor + ". Esperado 'dd de MMMM de yyyy HH:mm hs.'");
        }
    }
}
