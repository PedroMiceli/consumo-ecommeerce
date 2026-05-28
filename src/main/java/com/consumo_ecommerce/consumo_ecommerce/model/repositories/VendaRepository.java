package com.consumo_ecommerce.consumo_ecommerce.model.repositories;

import com.consumo_ecommerce.consumo_ecommerce.model.models.venda.Venda;
import com.consumo_ecommerce.consumo_ecommerce.model.repositories.projections.VendaProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface VendaRepository extends JpaRepository<Venda, UUID> {

    List<VendaProjection> findByDataVendaGreaterThanEqualAndDataVendaLessThan(LocalDateTime inicio, LocalDateTime fim);
}
