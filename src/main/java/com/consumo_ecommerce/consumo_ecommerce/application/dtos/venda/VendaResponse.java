package com.consumo_ecommerce.consumo_ecommerce.application.dtos.venda;

import com.consumo_ecommerce.consumo_ecommerce.application.dtos.anuncio.AnuncioResponse;
import com.consumo_ecommerce.consumo_ecommerce.model.repositories.projections.VendaProjection;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record VendaResponse(
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
        AnuncioResponse anuncio
) {

    public VendaResponse(VendaProjection venda) {
        this(
                venda.getId(),
                venda.getNumeroVenda(),
                venda.getDataVenda(),
                venda.getDeposito(),
                venda.getEstado(),
                venda.getDescricaoStatus(),
                venda.getPacoteDiversosProdutos(),
                venda.getPertenceKit(),
                venda.getUnidades(),
                venda.getReceitaProdutos(),
                venda.getReceitaAcrescimoPreco(),
                venda.getTaxaParcelamentoAcrescimo(),
                venda.getTarifaVendaImpostos(),
                venda.getReceitaEnvio(),
                venda.getTarifasEnvio(),
                venda.getCustoEnvioMedidasPeso(),
                venda.getCustoDiferencasMedidasPeso(),
                venda.getDescontosBonus(),
                venda.getCancelamentosReembolsos(),
                venda.getTotal(),
                venda.getMesFaturamentoTarifas(),
                venda.getPedidoCompra(),
                venda.getVendaPorPublicidade(),
                venda.getAnuncio() != null ? new AnuncioResponse(venda.getAnuncio()) : null
        );
    }
}
