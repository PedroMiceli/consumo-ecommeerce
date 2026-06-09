package com.consumo_ecommerce.consumo_ecommerce.application.dtos.venda;

import com.consumo_ecommerce.consumo_ecommerce.exceptions.CampoObrigatorioException;
import com.consumo_ecommerce.consumo_ecommerce.model.models.anuncio.Anuncio;
import com.consumo_ecommerce.consumo_ecommerce.model.models.venda.Venda;
import com.consumo_ecommerce.consumo_ecommerce.utils.Utils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record VendaRequest(
        UUID id,
        String numeroVenda,
        LocalDateTime dataVenda,
        String deposito,
        String estado,
        String descricaoStatus,
        Boolean pacoteDiversosProdutos,
        Boolean pertenceKit,
        Integer unidades,
        BigDecimal receitaProdutos,
        BigDecimal receitaAcrescimoPreco,
        BigDecimal taxaParcelamentoAcrescimo,
        BigDecimal tarifaVendaImpostos,
        BigDecimal receitaEnvio,
        BigDecimal tarifasEnvio,
        BigDecimal custoEnvioMedidasPeso,
        BigDecimal custoDiferencasMedidasPeso,
        BigDecimal descontosBonus,
        BigDecimal cancelamentosReembolsos,
        BigDecimal total,
        String mesFaturamentoTarifas,
        String pedidoCompra,
        Boolean vendaPorPublicidade,
        String numeroAnuncio,
        String sku,
        String canalVenda,
        String tituloAnuncio,
        String variacao,
        BigDecimal precoUnitarioVenda,
        String tipoAnuncio,
        String nfEmAnexo

) {
    public static Venda converter(VendaRequest request, Anuncio anuncio) {
        validar(request, anuncio);

        return new Venda(
                request.id,
                request.numeroVenda,
                request.dataVenda,
                request.deposito,
                request.estado,
                request.descricaoStatus,
                request.pacoteDiversosProdutos,
                request.pertenceKit,
                request.unidades,
                request.receitaProdutos,
                request.receitaAcrescimoPreco,
                request.taxaParcelamentoAcrescimo,
                request.tarifaVendaImpostos,
                request.receitaEnvio,
                request.tarifasEnvio,
                request.custoEnvioMedidasPeso,
                request.custoDiferencasMedidasPeso,
                request.descontosBonus,
                request.cancelamentosReembolsos,
                request.total,
                request.mesFaturamentoTarifas,
                request.pedidoCompra,
                Boolean.TRUE.equals(request.vendaPorPublicidade),
                anuncio,
                request.nfEmAnexo
        );
    }



    private static void validar(VendaRequest request, Anuncio anuncio) {
        if (request == null)
            throw new CampoObrigatorioException("Venda");

        if (Utils.valorNulo(request.numeroVenda))
            throw new CampoObrigatorioException("Número da venda");

        if (request.dataVenda == null)
            throw new CampoObrigatorioException("Data da venda");

        if (request.unidades == null)
            throw new CampoObrigatorioException("Unidades");

        if (request.total == null)
            throw new CampoObrigatorioException("Total");

        if (Utils.valorNulo(request.numeroAnuncio))
            throw new CampoObrigatorioException("Número do anúncio");

        if (anuncio == null)
            throw new CampoObrigatorioException("Anúncio");
    }
}
