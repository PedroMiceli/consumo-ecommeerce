package com.consumo_ecommerce.consumo_ecommerce.application.anuncio;

import com.consumo_ecommerce.consumo_ecommerce.application.dtos.anuncio.AnuncioResponse;
import com.consumo_ecommerce.consumo_ecommerce.application.dtos.anuncio.AnuncioRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface IAnuncioApplication {
    void salvarAnuncios(List<AnuncioRequest> anuncios);
    List<AnuncioResponse> buscarAnuncioPorId(UUID id);
}
