package com.consumo_ecommerce.consumo_ecommerce.application.mapper;

import com.consumo_ecommerce.consumo_ecommerce.application.dtos.venda.VendaRequest;
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
import java.text.Normalizer;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.ResolverStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class XlsxMapper {
    private static final int PRIMEIRA_LINHA_DADOS = 6;

    public List<VendaRequest> converterParaVendaRequest(MultipartFile arquivo) {
        try (InputStream inputStream = arquivo.getInputStream();
             Workbook workbook = WorkbookFactory.create(inputStream)) {

            Sheet sheet = workbook.getSheetAt(0);

            List<VendaRequest> vendas = new ArrayList<>();

            for (int i = PRIMEIRA_LINHA_DADOS; i <= sheet.getLastRowNum(); i++) {
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
                        getString(linha.getCell(23)),              // numeroAnuncio
                        getString(linha.getCell(22)),              // numeroAnuncio
                        getString(linha.getCell(24)),              // numeroAnuncio
                        getString(linha.getCell(25)),              // numeroAnuncio
                        getString(linha.getCell(26)),              // numeroAnuncio
                        getBigDecimal(linha.getCell(27)),              // numeroAnuncio
                        getString(linha.getCell(28))            // numeroAnuncio
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
        if (cell == null || cell.getCellType() == CellType.BLANK) {
            return BigDecimal.ZERO;
        }

        try {
            if (cell.getCellType() == CellType.NUMERIC) {
                return BigDecimal.valueOf(cell.getNumericCellValue());
            }

            if (cell.getCellType() == CellType.STRING) {
                String valor = cell.getStringCellValue();

                if (valor == null || valor.isBlank()) {
                    return BigDecimal.ZERO;
                }

                valor = valor.trim()
                        .replace("R$", "")
                        .replace(".", "")
                        .replace(",", ".")
                        .replaceAll("\\s+", "");

                if (valor.isBlank() || valor.equals("-")) {
                    return BigDecimal.ZERO;
                }

                return new BigDecimal(valor);
            }

            return BigDecimal.ZERO;

        } catch (Exception ex) {
            throw new RuntimeException("Valor numérico inválido na célula: " + cell, ex);
        }
    }

    private Integer getInteger(Cell cell) {
        if (cell == null || cell.getCellType() == CellType.BLANK) {
            return 0;
        }

        try {
            if (cell.getCellType() == CellType.NUMERIC) {
                return (int) cell.getNumericCellValue();
            }

            if (cell.getCellType() == CellType.STRING) {
                String valor = cell.getStringCellValue();

                if (valor == null || valor.isBlank()) {
                    return 0;
                }

                valor = valor.trim()
                        .replaceAll("\\.0$", "")
                        .replaceAll("\\s+", "");

                if (valor.isBlank() || valor.equals("-")) {
                    return 0;
                }

                return Integer.parseInt(valor);
            }

            return 0;

        } catch (Exception ex) {
            throw new RuntimeException("Valor inteiro inválido na célula: " + cell, ex);
        }
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
        if (cell == null || cell.getCellType() == CellType.BLANK) {
            return null;
        }

        try {
            if (cell.getCellType() == CellType.NUMERIC && DateUtil.isCellDateFormatted(cell)) {
                return cell.getLocalDateTimeCellValue();
            }

            String valorOriginal = getString(cell);

            if (valorOriginal == null || valorOriginal.isBlank()) {
                return null;
            }

            String valor = valorOriginal
                    .trim()
                    .toLowerCase(Locale.ROOT)
                    .replace("\u00A0", " ")
                    .replace("\u200B", "")
                    .replace("\uFEFF", "")
                    .replaceAll("\\s+", " ")
                    .replaceAll("\\s*hs\\.*\\s*$", "")
                    .trim();

            valor = Normalizer.normalize(valor, Normalizer.Form.NFD)
                    .replaceAll("\\p{M}", "");

            Pattern pattern = Pattern.compile(
                    "^(\\d{1,2})\\s+de\\s+([a-z]+)\\s+de\\s+(\\d{4})\\s+(\\d{1,2}):(\\d{2})$"
            );

            Matcher matcher = pattern.matcher(valor);

            if (!matcher.matches()) {
                throw new RuntimeException("Formato não reconhecido após normalização: [" + valor + "]");
            }

            int dia = Integer.parseInt(matcher.group(1));
            int mes = obterNumeroMes(matcher.group(2));
            int ano = Integer.parseInt(matcher.group(3));
            int hora = Integer.parseInt(matcher.group(4));
            int minuto = Integer.parseInt(matcher.group(5));

            return LocalDateTime.of(ano, mes, dia, hora, minuto);

        } catch (Exception ex) {
            throw new RuntimeException(
                    "Formato de data inválido para: [" + getString(cell) + "]. Causa: " + ex.getMessage(),
                    ex
            );
        }
    }

    private static int obterNumeroMes(String mes) {
        return switch (mes.toLowerCase(Locale.ROOT)) {
            case "janeiro" -> 1;
            case "fevereiro" -> 2;
            case "março", "marco" -> 3;
            case "abril" -> 4;
            case "maio" -> 5;
            case "junho" -> 6;
            case "julho" -> 7;
            case "agosto" -> 8;
            case "setembro" -> 9;
            case "outubro" -> 10;
            case "novembro" -> 11;
            case "dezembro" -> 12;
            default -> throw new RuntimeException("Mês inválido: " + mes);
        };
    }
}
