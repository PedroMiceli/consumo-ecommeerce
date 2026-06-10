package com.consumo_ecommerce.consumo_ecommerce.model.repositories;

import com.consumo_ecommerce.consumo_ecommerce.model.models.produto.Produto;
import com.consumo_ecommerce.consumo_ecommerce.model.repositories.projections.ProdutoProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Set;
import java.util.UUID;

public interface ProdutoRepository extends JpaRepository<Produto, UUID> {

    @Query("""
        SELECT 
            item.id AS id,
            item.sku AS sku,
            item.nome AS nome,
            item.custo AS custo
        FROM Produto item
        WHERE item.sku IN :skus
            AND item.dataExcluido IS NULL
    """)
    Set<Produto> buscarProdutosExistentesPorSku(@Param("skus") Set<String> skus);
}
