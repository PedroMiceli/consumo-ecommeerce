package com.consumo_ecommerce.consumo_ecommerce.service.produto;

import com.consumo_ecommerce.consumo_ecommerce.model.models.anuncio.Anuncio;
import com.consumo_ecommerce.consumo_ecommerce.model.models.produto.Produto;
import com.consumo_ecommerce.consumo_ecommerce.model.repositories.projections.AnuncioProjection;
import com.consumo_ecommerce.consumo_ecommerce.model.repositories.projections.ProdutoProjection;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public interface IProdutoService {
    List<Produto> buscarPorSKUs(Collection<String> numerosSKUs);
}
