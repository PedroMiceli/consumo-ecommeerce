package com.consumo_ecommerce.consumo_ecommerce.model.repositories;

import com.consumo_ecommerce.consumo_ecommerce.model.models.venda.VendaImportacaoErro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Set;
import java.util.UUID;

public interface VendaImportacaoErroRepository extends JpaRepository<VendaImportacaoErro, UUID> {

    @Query("""
        SELECT erro.numeroVenda
        FROM VendaImportacaoErro erro
        WHERE erro.numeroVenda IN :numerosVenda
            AND erro.dataExcluido IS NULL
    """)
    Set<String> buscarNumerosVendaExistentes(@Param("numerosVenda") Set<String> numerosVenda);

}
