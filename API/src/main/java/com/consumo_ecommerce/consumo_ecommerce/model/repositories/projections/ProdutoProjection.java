package com.consumo_ecommerce.consumo_ecommerce.model.repositories.projections;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public interface ProdutoProjection {
    UUID getId();

    String getSku();

    String getNome();

    BigDecimal getCusto();
}
