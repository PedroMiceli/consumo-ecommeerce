package com.consumo_ecommerce.consumo_ecommerce.service.anuncio;

import com.consumo_ecommerce.consumo_ecommerce.model.models.anuncio.Anuncio;
import com.consumo_ecommerce.consumo_ecommerce.model.repositories.projections.AnuncioProjection;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public interface IAnuncioService {
    List<Anuncio> salvarAnuncios(List<Anuncio> anuncios);
    Anuncio buscarPorNumeroAnuncio(String numeroAnuncio);
    List<AnuncioProjection> buscarAnuncios();
    List<Anuncio> buscarPorNumerosAnuncio(Collection<String> numerosAnuncio);
}
