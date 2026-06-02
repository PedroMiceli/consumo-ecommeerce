package com.consumo_ecommerce.consumo_ecommerce.application.venda;

import com.consumo_ecommerce.consumo_ecommerce.application.dtos.venda.VendaResponse;
import com.consumo_ecommerce.consumo_ecommerce.application.dtos.venda.VendaRequest;
import com.consumo_ecommerce.consumo_ecommerce.application.mapper.XlsxMapper;
import com.consumo_ecommerce.consumo_ecommerce.exceptions.CampoObrigatorioException;
import com.consumo_ecommerce.consumo_ecommerce.exceptions.NaoEncontradoException;
import com.consumo_ecommerce.consumo_ecommerce.model.models.anuncio.Anuncio;
import com.consumo_ecommerce.consumo_ecommerce.model.models.venda.Venda;
import com.consumo_ecommerce.consumo_ecommerce.model.models.venda.VendaImportacaoErro;
import com.consumo_ecommerce.consumo_ecommerce.service.anuncio.IAnuncioService;
import com.consumo_ecommerce.consumo_ecommerce.service.venda.IVendaService;
import com.consumo_ecommerce.consumo_ecommerce.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class VendaApplication implements IVendaApplication{

    @Autowired
    private IVendaService vendaService;

    @Autowired
    private IAnuncioService anuncioService;

    @Autowired
    private XlsxMapper xlsxMapper;

    //Le o arquivo xlsx e retorna uma lista de VendaRequest, chamando a funçao de salvar
    @Override
    public void importarVendasXlsx(MultipartFile arquivo) {
        if (arquivo == null || arquivo.isEmpty()) {
            throw new CampoObrigatorioException("Arquivo XLSX");
        }
        List<VendaRequest> vendasRequest = xlsxMapper.converterParaVendaRequest(arquivo);
        salvarVendas(vendasRequest);
    }

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

        //Busca no banco os anúncios que já existem
        List<Anuncio> anuncios = anuncioService.buscarPorNumerosAnuncio(numerosAnuncio);
        //Monta o mapa com os anuncios existentes
        Map<String, Anuncio> anunciosPorNumero = anuncios.stream()
                .collect(Collectors.toMap(
                        Anuncio::getNumeroAnuncio,
                        anuncio -> anuncio
                ));

        //Descobre quais anúncios ainda não existem
        Set<String> numerosAnuncioNaoEncontrados = numerosAnuncio.stream()
                .filter(numeroAnuncio -> !anunciosPorNumero.containsKey(numeroAnuncio))
                .collect(Collectors.toSet());

        //Cria os anúncios faltantes
        List<Anuncio> anunciosCriados = numerosAnuncioNaoEncontrados.stream()
                .map(this::criarAnuncioAutomatico)
                .toList();

        //Salva os anúncios criados
        if (!anunciosCriados.isEmpty()) {
            List<Anuncio> anunciosSalvos = anuncioService.salvarAnuncios(anunciosCriados);
            anunciosSalvos.forEach(anuncio ->
                    anunciosPorNumero.put(anuncio.getNumeroAnuncio(), anuncio)
            );
        }

        // Busca todos os números de venda informados na importação
        Set<String> numerosVenda = vendasRequest.stream()
                .map(VendaRequest::numeroVenda)
                .filter(numero -> !Utils.valorNulo(numero))
                .collect(Collectors.toSet());

        // Busca no banco as vendas que já existem
        Set<String> numerosVendaExistentes = vendaService.buscarNumerosVendaExistentes(numerosVenda);
        // Evita duplicidade dentro da própria importação atual
        Set<String> numerosVendaProcessadosNaImportacao = new HashSet<>();


        List<Venda> vendasValidas = new ArrayList<>();
        List<VendaImportacaoErro> vendasComErro = new ArrayList<>();

        for (VendaRequest request : vendasRequest) {
            if (request.numeroVenda() == null) {
                continue;
            }

            String numeroVenda = request.numeroVenda();

            if (!Utils.valorNulo(numeroVenda)) {
                // Venda já existe no banco, então ignora
                if (numerosVendaExistentes.contains(numeroVenda)) {
                    continue;
                }
                // Venda repetida dentro do próprio arquivo/importação, então ignora
                if (!numerosVendaProcessadosNaImportacao.add(numeroVenda)) {
                    continue;
                }
            }

            Anuncio anuncio = anunciosPorNumero.get(request.numeroAnuncio());

            if (!Utils.valorNulo(request.numeroVenda()) && (anuncio == null || anuncio.getNumeroAnuncio().isEmpty())){
                VendaImportacaoErro erro = VendaImportacaoErro.converter(request, "Venda importada sem número de anúncio informado.");
                vendasComErro.add(erro);
                continue;
            }

            Venda venda = VendaRequest.converter(request, anuncio);
            vendasValidas.add(venda);
        }

        if (!vendasValidas.isEmpty()) {
            vendaService.salvarVendas(vendasValidas);
        }

        if (!vendasComErro.isEmpty()) {
            vendaService.salvarVendaImportacaoErro(vendasComErro);
        }
    }

    @Override
    public List<VendaResponse> buscarVendas(LocalDateTime dataInicio, LocalDateTime dataFim) {
        return vendaService.buscarVendas(dataInicio, dataFim).stream().map(VendaResponse::new).toList();
    }

    private Anuncio criarAnuncioAutomatico(String numeroAnuncio) {
        Anuncio anuncio = new Anuncio();

        anuncio.setNumeroAnuncio(numeroAnuncio);

        // Dados temporários/default, porque a venda só possui o numeroAnuncio
        anuncio.setSku("SKU-NAO-INFORMADO-" + numeroAnuncio);
        anuncio.setCanalVenda("Não informado");
        anuncio.setTituloAnuncio("Anúncio criado automaticamente");
        anuncio.setVariacao("Não informado");
        anuncio.setPrecoUnitarioVenda(BigDecimal.ZERO);
        anuncio.setTipoAnuncio("Não informado");

        return anuncio;
    }

}


