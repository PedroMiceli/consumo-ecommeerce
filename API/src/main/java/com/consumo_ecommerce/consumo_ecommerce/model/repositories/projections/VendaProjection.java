package com.consumo_ecommerce.consumo_ecommerce.model.repositories.projections;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public interface VendaProjection {
    UUID getId();

    String getNumeroVenda();

    LocalDateTime getDataVenda();

    String getDeposito();

    String getEstado();

    String getDescricaoStatus();

    Boolean getPacoteDiversosProdutos();

    Boolean getPertenceKit();

    Integer getUnidades();

    BigDecimal getReceitaProdutos();

    BigDecimal getReceitaAcrescimoPreco();

    BigDecimal getTaxaParcelamentoAcrescimo();

    BigDecimal getTarifaVendaImpostos();

    BigDecimal getReceitaEnvio();

    BigDecimal getTarifasEnvio();

    BigDecimal getCustoEnvioMedidasPeso();

    BigDecimal getCustoDiferencasMedidasPeso();

    BigDecimal getDescontosBonus();

    BigDecimal getCancelamentosReembolsos();

    BigDecimal getTotal();

    String getMesFaturamentoTarifas();

    String getPedidoCompra();

    Boolean getVendaPorPublicidade();

    AnuncioProjection getAnuncio();

    String getNfEmAnexo();
}
