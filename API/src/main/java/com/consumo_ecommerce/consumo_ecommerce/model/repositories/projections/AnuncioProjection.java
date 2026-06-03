package com.consumo_ecommerce.consumo_ecommerce.model.repositories.projections;

import java.math.BigDecimal;
import java.util.UUID;

public interface AnuncioProjection {
    UUID getId();

    String getSku();

    String getNumeroAnuncio();

    String getCanalVenda();

    String getTituloAnuncio();

    String getVariacao();

    BigDecimal getPrecoUnitarioVenda();

    String getTipoAnuncio();
}
