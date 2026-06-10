package com.consumo_ecommerce.consumo_ecommerce.application.venda;

import com.consumo_ecommerce.consumo_ecommerce.application.dtos.ResumoDashBoard;
import com.consumo_ecommerce.consumo_ecommerce.application.dtos.venda.VendaResponse;
import com.consumo_ecommerce.consumo_ecommerce.application.dtos.venda.VendaRequest;
import com.consumo_ecommerce.consumo_ecommerce.application.mapper.XlsxMapper;
import com.consumo_ecommerce.consumo_ecommerce.exceptions.CampoObrigatorioException;
import com.consumo_ecommerce.consumo_ecommerce.exceptions.NaoEncontradoException;
import com.consumo_ecommerce.consumo_ecommerce.model.models.anuncio.Anuncio;
import com.consumo_ecommerce.consumo_ecommerce.model.models.produto.Produto;
import com.consumo_ecommerce.consumo_ecommerce.model.models.venda.Venda;
import com.consumo_ecommerce.consumo_ecommerce.model.models.venda.VendaImportacaoErro;
import com.consumo_ecommerce.consumo_ecommerce.model.repositories.projections.ProdutoProjection;
import com.consumo_ecommerce.consumo_ecommerce.model.repositories.projections.VendaProjection;
import com.consumo_ecommerce.consumo_ecommerce.service.anuncio.IAnuncioService;
import com.consumo_ecommerce.consumo_ecommerce.service.produto.IProdutoService;
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
    private IProdutoService produtoService;

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

        Set<String> skusProdutos = vendasRequest.stream().map(VendaRequest::sku).collect(Collectors.toSet());



        // Busca todos os números de anúncio informados na importação
        Set<String> numerosAnuncio = vendasRequest.stream()
                .map(VendaRequest::numeroAnuncio)
                .filter(numero -> !Utils.valorNulo(numero))
                .collect(Collectors.toSet());

        /*
         * Mapa usado para criar automaticamente anúncios ainda não cadastrados.
         * Como os dados do anúncio estão dentro da venda, guardamos uma venda de referência
         * para cada numeroAnuncio.
         */
        Map<String, VendaRequest> vendaRequestPorNumeroAnuncio = new HashMap<>();
        Map<String, VendaRequest> vendaRequestPorSku = new HashMap<>();

        for (VendaRequest request : vendasRequest) {
            if (!Utils.valorNulo(request.numeroAnuncio())) {
                vendaRequestPorNumeroAnuncio.putIfAbsent(request.numeroAnuncio(), request);
            }

            if (!Utils.valorNulo(request.sku())) {
                vendaRequestPorSku.putIfAbsent(request.sku(), request);
            }
        }

        // Busca no banco os anúncios que já existem
        List<Anuncio> anuncios = anuncioService.buscarPorNumerosAnuncio(numerosAnuncio);
        // Busca no banco os produtos que já existem
        List<Produto> produtos = produtoService.buscarPorSKUs(skusProdutos);

        // Monta o mapa com os anúncios existentes
        Map<String, Anuncio> anunciosPorNumero = anuncios.stream()
                .collect(Collectors.toMap(
                        Anuncio::getNumeroAnuncio,
                        anuncio -> anuncio
                ));

        // Monta o mapa com os produtos existentes
        Map<String, Produto> produtosPorSku = produtos.stream()
                .collect(Collectors.toMap(
                        Produto::getSku,
                        produto -> produto
                ));

        // Descobre quais anúncios ainda não existem
        Set<String> numerosAnuncioNaoEncontrados = numerosAnuncio.stream()
                .filter(numeroAnuncio -> !anunciosPorNumero.containsKey(numeroAnuncio))
                .collect(Collectors.toSet());

        // Descobre quais produtos ainda não existem
        Set<String> skusProdutosNaoEncontrados = skusProdutos.stream()
                .filter(sku -> !produtosPorSku.containsKey(sku))
                .collect(Collectors.toSet());

        // Cria os anúncios faltantes
        List<Anuncio> anunciosCriados = numerosAnuncioNaoEncontrados.stream()
                .map(vendaRequestPorNumeroAnuncio::get)
                .filter(Objects::nonNull)
                .map(this::criarAnuncioAutomaticoNovo)
                .toList();

        // Cria os produtos faltantes
        List<Produto> produtosCriados = skusProdutosNaoEncontrados.stream()
                .map(vendaRequestPorSku::get)
                .filter(Objects::nonNull)
                .map(this::criarProdutoAutomaticoNovo)
                .toList();

        // Salva os anúncios criados e adiciona no mapa
        if (!anunciosCriados.isEmpty()) {
            List<Anuncio> anunciosSalvos = anuncioService.salvarAnuncios(anunciosCriados);

            anunciosSalvos.forEach(anuncio ->
                    anunciosPorNumero.put(anuncio.getNumeroAnuncio(), anuncio)
            );
        }

        // Salva os produtos criados e adiciona no mapa
        if (!produtosCriados.isEmpty()) {
            List<Anuncio> produtossalvos = anuncioService.salvarAnuncios(anunciosCriados);

            produtossalvos.forEach(anuncio ->
                    anunciosPorNumero.put(anuncio.getNumeroAnuncio(), anuncio)
            );
        }

        // Busca todos os números de venda informados na importação
        Set<String> numerosVenda = vendasRequest.stream()
                .map(VendaRequest::numeroVenda)
                .filter(numero -> !Utils.valorNulo(numero))
                .collect(Collectors.toSet());

        // Busca no banco as vendas que já existem na tabela oficial
        Set<String> numerosVendaExistentes = vendaService.buscarNumerosVendaExistentes(numerosVenda);

        // Busca no banco as vendas que já existem na tabela de erro
        Set<String> vendasComErroJaSalvas = vendaService.buscarNumerosVendaExistentesComErro(numerosVenda);

        // Evita duplicidade dentro da própria importação atual
        Set<String> numerosVendaProcessadosNaImportacao = new HashSet<>();
        Set<String> vendasComErroProcessadasNaImportacao = new HashSet<>();

        List<Venda> vendasValidas = new ArrayList<>();
        List<VendaImportacaoErro> vendasComErro = new ArrayList<>();

        for (VendaRequest request : vendasRequest) {
            String numeroVenda = request.numeroVenda();

            if (Utils.valorNulo(numeroVenda)) {
                continue;
            }

            /*
             * Ignora venda já existente no banco
             * ou repetida dentro da própria importação.
             */
            if (numerosVendaExistentes.contains(numeroVenda)
                    || !numerosVendaProcessadosNaImportacao.add(numeroVenda)) {
                continue;
            }

            Anuncio anuncio = anunciosPorNumero.get(request.numeroAnuncio());

            /*
             * Neste ponto, se anuncio for null, significa que o numeroAnuncio veio nulo/vazio,
             * pois anúncios não encontrados foram criados automaticamente antes.
             */
            if (anuncio == null || Utils.valorNulo(anuncio.getNumeroAnuncio())) {
                boolean erroJaExisteNoBanco = vendasComErroJaSalvas.contains(numeroVenda);
                boolean erroRepetidoNestaImportacao = !vendasComErroProcessadasNaImportacao.add(numeroVenda);

                if (erroJaExisteNoBanco || erroRepetidoNestaImportacao) {
                    continue;
                }

                VendaImportacaoErro erro = VendaImportacaoErro.converter(
                        request,
                        "Venda importada sem número de anúncio informado."
                );

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

    @Override
    public ResumoDashBoard buscarResumoVendas(LocalDateTime dataInicio, LocalDateTime dataFim) {
        try {
            List<VendaProjection> vendas = vendaService.buscarVendas(dataInicio, dataFim);
            return ResumoDashBoard.converter(vendas);
        }catch (Exception ex){
            throw new RuntimeException("Erro ao buscar resumo das vendas", ex);
        }
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

    private Anuncio criarAnuncioAutomaticoNovo(VendaRequest request) {
        Anuncio anuncio = new Anuncio();

        anuncio.setNumeroAnuncio(request.numeroAnuncio());
        anuncio.setSku(request.sku());
        anuncio.setCanalVenda(request.canalVenda());
        anuncio.setTituloAnuncio(request.tituloAnuncio());
        anuncio.setVariacao(request.variacao());
        anuncio.setPrecoUnitarioVenda(request.precoUnitarioVenda());
        anuncio.setTipoAnuncio(request.tipoAnuncio());

        return anuncio;
    }

    private Produto criarProdutoAutomaticoNovo(VendaRequest request) {
        Produto produto = new Produto();

        produto.setSku(request.sku());
        produto.setNome(request.tituloAnuncio());
        // Como você comentou que será preenchido manualmente depois
        produto.setCusto(BigDecimal.ZERO);

        return produto;
    }

}


