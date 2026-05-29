package com.consumo_ecommerce.consumo_ecommerce.service.anuncio;

import com.consumo_ecommerce.consumo_ecommerce.exceptions.NaoEncontradoException;
import com.consumo_ecommerce.consumo_ecommerce.model.models.anuncio.Anuncio;
import com.consumo_ecommerce.consumo_ecommerce.model.repositories.AnuncioRepository;
import com.consumo_ecommerce.consumo_ecommerce.model.repositories.projections.AnuncioProjection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class AnuncioService implements IAnuncioService{

    @Autowired
    private AnuncioRepository anuncioRepository;

    @Override
    public void salvarAnuncios(List<Anuncio> anuncios) {
        anuncioRepository.saveAll(anuncios);
    }

    @Override
    public Anuncio buscarPorNumeroAnuncio(String numeroAnuncio) {
        return anuncioRepository.findByNumeroAnuncio(numeroAnuncio)
                .orElseThrow(() -> new NaoEncontradoException("Anúncio não encontrado."));
    }

    @Override
    public List<AnuncioProjection> buscarAnuncios(){
        return  anuncioRepository.findAllProjectedBy();
    }

    @Override
    public List<Anuncio> buscarPorNumerosAnuncio(Collection<String> numerosAnuncio) {
        return anuncioRepository.findByNumeroAnuncioIn(numerosAnuncio);
    }
}
