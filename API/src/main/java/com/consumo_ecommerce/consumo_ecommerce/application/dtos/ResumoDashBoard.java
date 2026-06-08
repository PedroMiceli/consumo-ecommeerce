package com.consumo_ecommerce.consumo_ecommerce.application.dtos;

import com.consumo_ecommerce.consumo_ecommerce.application.dtos.anuncio.AnuncioResponse;
import com.consumo_ecommerce.consumo_ecommerce.model.repositories.projections.VendaProjection;

import java.math.BigDecimal;
import java.util.List;

public record ResumoDashBoard(
        int totalDeVendas,
        BigDecimal valorFaturado,
        BigDecimal valorLucrado,
        BigDecimal ROI,
        BigDecimal valorDeclarado
) {

    public ResumoDashBoard(List<VendaProjection> vendas) {
        this();
    }

    private BigDecimal calcularFaturamento(List<VendaProjection> vendas) {
        BigDecimal valorFaturado = BigDecimal.ZERO;
        BigDecimal valorEmConta = BigDecimal.ZERO;
        BigDecimal valorDeclarado = BigDecimal.ZERO;

        for (VendaProjection venda : vendas) {
            valorFaturado = valorFaturado.add(venda.getReceitaProdutos());
        }
        return valorFaturado;
    }

    private BigDecimal calcularLucro(List<VendaProjection> vendas){}

    private BigDecimal calcularROI(List<VendaProjection> vendas){}

    private BigDecimal calcularValorDeclarado(List<VendaProjection> vendas){}
}
