package com.consumo_ecommerce.consumo_ecommerce.model.repositories;

import com.consumo_ecommerce.consumo_ecommerce.model.models.venda.VendaImportacaoErro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface VendaImportacaoErroRepository extends JpaRepository<VendaImportacaoErro, UUID> {
}
