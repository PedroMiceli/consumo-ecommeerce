package com.consumo_ecommerce.consumo_ecommerce.application.dtos;

import com.consumo_ecommerce.consumo_ecommerce.model.repositories.projections.VendaProjection;

import java.math.BigDecimal;
import java.util.List;

public record ResumoDashBoard(
        int totalDeVendasRealizadas,
        int totalDeVendasBemSucedidas,
        BigDecimal valorFaturado,
        BigDecimal valorLucrado,
        BigDecimal valorDeclarado,
        BigDecimal valorDevolvidoComReembolsoAoComprador,
        float porcentagemDeVendasComReembolso
) {

    public static ResumoDashBoard converter(List<VendaProjection> vendas) {
        int totalDeVendas = vendas.size();
        int totalDeVendasBemSucedidas = 0;

        BigDecimal valorFaturado = BigDecimal.ZERO;
        BigDecimal valorLucrado = BigDecimal.ZERO;
        BigDecimal valorDeclarado = BigDecimal.ZERO;
        BigDecimal valorDevolvidoComReembolsoAoComprador = BigDecimal.ZERO;

        for (VendaProjection venda : vendas) {
            String estado = venda.getEstado();

            boolean vendaBemSucedida =
                    "Entregue".equals(estado)
                            || "Venda entregue".equals(estado)
                            || "Mediação finalizada. Te demos o dinheiro.".equals(estado);

            if (vendaBemSucedida) {
                totalDeVendasBemSucedidas++;

                valorFaturado = valorFaturado.add(valorOuZero(venda.getReceitaProdutos()));
                valorLucrado = valorLucrado.add(valorOuZero(venda.getTotal()));

                if ("Autorizada".equals(venda.getNfEmAnexo())) {
                    valorDeclarado = valorDeclarado.add(valorOuZero(venda.getReceitaEnvio()));
                }
            }

            if ("Devolução finalizada com reembolso para o comprador".equals(estado)) {
                valorDevolvidoComReembolsoAoComprador =
                        valorDevolvidoComReembolsoAoComprador.add(valorOuZero(venda.getTotal()));
            }
        }



        return new ResumoDashBoard(
                totalDeVendas,
                totalDeVendasBemSucedidas,
                valorFaturado,
                valorLucrado,
                valorDeclarado,
                valorDevolvidoComReembolsoAoComprador,
                calcularProporcaoErros(totalDeVendas, totalDeVendasBemSucedidas)
        );
    }

    private static BigDecimal valorOuZero(BigDecimal valor) {
        return valor != null ? valor : BigDecimal.ZERO;
    }

    private static float calcularProporcaoErros(Integer totalDeVendas, Integer totalDeVendasBemSucedidas) {
        if (totalDeVendas == null || totalDeVendasBemSucedidas == null)
            return 0f;
        if (totalDeVendas <= 0 || totalDeVendasBemSucedidas < 0)
            return 0f;
        float proporcao = ((totalDeVendas - totalDeVendasBemSucedidas) * 100f) / totalDeVendas;
        if (Float.isNaN(proporcao) || Float.isInfinite(proporcao))
            return 0f;
        return proporcao;
    }
}
