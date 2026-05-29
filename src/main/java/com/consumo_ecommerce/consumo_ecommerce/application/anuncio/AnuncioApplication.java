package com.consumo_ecommerce.consumo_ecommerce.application.anuncio;

import com.consumo_ecommerce.consumo_ecommerce.application.dtos.anuncio.AnuncioResponse;
import com.consumo_ecommerce.consumo_ecommerce.application.dtos.anuncio.AnuncioRequest;
import com.consumo_ecommerce.consumo_ecommerce.model.models.anuncio.Anuncio;
import com.consumo_ecommerce.consumo_ecommerce.service.anuncio.IAnuncioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnuncioApplication implements IAnuncioApplication{

    @Autowired
    private IAnuncioService anuncioService;

    @Override
    public void salvarAnuncios(List<AnuncioRequest> anuncios) {
        List<Anuncio> anunciosConvertidos = anuncios.stream().map(AnuncioRequest::converter).toList();
        anuncioService.salvarAnuncios(anunciosConvertidos);
    }

    @Override
    public List<AnuncioResponse> buscarAnuncios() {
        return anuncioService.buscarAnuncios().stream().map(AnuncioResponse::new).toList();
    }


}
