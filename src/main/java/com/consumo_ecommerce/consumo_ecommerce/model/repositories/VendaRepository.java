package com.consumo_ecommerce.consumo_ecommerce.model.repositories;

import com.consumo_ecommerce.consumo_ecommerce.model.models.venda.Venda;
import com.consumo_ecommerce.consumo_ecommerce.model.repositories.projections.VendaProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Repository
public interface VendaRepository extends JpaRepository<Venda, UUID> {

    List<VendaProjection> findByDataVendaGreaterThanEqualAndDataVendaLessThan(LocalDateTime inicio, LocalDateTime fim);

    @Query("""
        SELECT item.numeroVenda
        FROM Venda item
        WHERE item.numeroVenda IN :numerosVenda
            AND item.dataExcluido IS NULL
    """)
    Set<String> buscarNumerosVendaExistentes(@Param("numerosVenda") Set<String> numerosVenda);
}
