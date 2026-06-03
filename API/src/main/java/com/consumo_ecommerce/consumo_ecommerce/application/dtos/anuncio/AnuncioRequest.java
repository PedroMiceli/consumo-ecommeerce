package com.consumo_ecommerce.consumo_ecommerce.application.dtos.anuncio;

import com.consumo_ecommerce.consumo_ecommerce.application.dtos.venda.VendaRequest;
import com.consumo_ecommerce.consumo_ecommerce.exceptions.CampoObrigatorioException;
import com.consumo_ecommerce.consumo_ecommerce.model.models.anuncio.Anuncio;
import com.consumo_ecommerce.consumo_ecommerce.utils.Utils;


import java.math.BigDecimal;
import java.util.UUID;

public record AnuncioRequest(
        UUID id,
        String sku,
        String numeroAnuncio,
        String canalVenda,
        String tituloAnuncio,
        String variacao,
        BigDecimal precoUnitarioVenda,
        String tipoAnuncio
) {
    public static Anuncio converter(AnuncioRequest request) {
        validar(request);

        return new Anuncio(
                request.id,
                request.sku,
                request.numeroAnuncio,
                request.canalVenda,
                request.tituloAnuncio,
                request.variacao,
                request.precoUnitarioVenda,
                request.tipoAnuncio
        );
    }


    private static void validar(AnuncioRequest request) {
        if (Utils.valorNulo(request.sku))
            throw new CampoObrigatorioException("SKU");

    }


}
