package com.consumo_ecommerce.consumo_ecommerce.model.repositories;

import com.consumo_ecommerce.consumo_ecommerce.model.models.anuncio.Anuncio;
import com.consumo_ecommerce.consumo_ecommerce.model.repositories.projections.AnuncioProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AnuncioRepository extends JpaRepository<Anuncio, UUID> {

    List<AnuncioProjection> findAllProjectedBy();
    Optional<Anuncio> findByNumeroAnuncio(String numeroAnuncio);
    List<Anuncio> findByNumeroAnuncioIn(Collection<String> numerosAnuncio);
}
