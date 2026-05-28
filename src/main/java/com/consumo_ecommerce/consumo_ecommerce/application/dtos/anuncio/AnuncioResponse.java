package com.consumo_ecommerce.consumo_ecommerce.application.dtos.anuncio;

import com.consumo_ecommerce.consumo_ecommerce.model.repositories.projections.AnuncioProjection;

import java.math.BigDecimal;
import java.util.UUID;

public record AnuncioResponse(
        UUID id,
        String sku,
        String numeroAnuncio,
        String canalVenda,
        String tituloAnuncio,
        String variacao,
        BigDecimal precoUnitarioVenda,
        String tipoAnuncio
) {
    public AnuncioResponse(AnuncioProjection anuncio) {
        this(
                anuncio.getId(),
                anuncio.getSku(),
                anuncio.getNumeroAnuncio(),
                anuncio.getCanalVenda(),
                anuncio.getTituloAnuncio(),
                anuncio.getVariacao(),
                anuncio.getPrecoUnitarioVenda(),
                anuncio.getTipoAnuncio()
        );
    }
}
