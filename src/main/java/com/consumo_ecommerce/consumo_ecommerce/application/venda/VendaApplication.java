package com.consumo_ecommerce.consumo_ecommerce.application.venda;

import com.consumo_ecommerce.consumo_ecommerce.application.dtos.venda.VendaResponse;
import com.consumo_ecommerce.consumo_ecommerce.application.dtos.venda.VendaRequest;
import com.consumo_ecommerce.consumo_ecommerce.exceptions.CampoObrigatorioException;
import com.consumo_ecommerce.consumo_ecommerce.exceptions.NaoEncontradoException;
import com.consumo_ecommerce.consumo_ecommerce.model.models.anuncio.Anuncio;
import com.consumo_ecommerce.consumo_ecommerce.model.models.venda.Venda;
import com.consumo_ecommerce.consumo_ecommerce.service.anuncio.IAnuncioService;
import com.consumo_ecommerce.consumo_ecommerce.service.venda.IVendaService;
import com.consumo_ecommerce.consumo_ecommerce.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class VendaApplication implements IVendaApplication{

    @Autowired
    private IVendaService vendaService;

    @Autowired
    private IAnuncioService anuncioService;

    @Override
    public void salvarVendas(List<VendaRequest> vendasRequest) {
        if (vendasRequest == null || vendasRequest.isEmpty()) {
            throw new CampoObrigatorioException("Vendas");
        }

        //Busca todos os numeros de numeroAnuncio e traz do banco cada Anuncio e salva em um mapa (Evitar N+1)
        Set<String> numerosAnuncio = vendasRequest.stream()
                .map(VendaRequest::numeroAnuncio)
                .filter(numero -> !Utils.valorNulo(numero))
                .collect(Collectors.toSet());
        List<Anuncio> anuncios = anuncioService.buscarPorNumerosAnuncio(numerosAnuncio);
        Map<String, Anuncio> anunciosPorNumero = anuncios.stream()
                .collect(Collectors.toMap(
                        Anuncio::getNumeroAnuncio,
                        anuncio -> anuncio
                ));


        List<Venda> vendas = vendasRequest.stream()
                .map(request -> {
                    //Verifica se o anuncio existe para o numeroAnuncio
                    Anuncio anuncio = anunciosPorNumero.get(request.numeroAnuncio());
                    if (anuncio == null) {
                        throw new NaoEncontradoException("Anúncio não encontrado para o número: " + request.numeroAnuncio());
                    }
                    return VendaRequest.converter(request, anuncio);
                })
                .toList();


        vendaService.salvarVendas(vendas);
    }

    @Override
    public List<VendaResponse> buscarVendas(LocalDateTime dataInicio, LocalDateTime dataFim) {
        return vendaService.buscarVendas(dataInicio, dataFim).stream().map(VendaResponse::new).toList();
    }
}


