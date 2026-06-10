package com.consumo_ecommerce.consumo_ecommerce.service.produto;

import com.consumo_ecommerce.consumo_ecommerce.model.models.produto.Produto;
import com.consumo_ecommerce.consumo_ecommerce.model.repositories.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Service
public class ProdutoService implements IProdutoService{

    @Autowired
    private ProdutoRepository produtoRepository;

    @Override
    public List<Produto> buscarPorSKUs(Set<String> numerosSKUs) {
        return produtoRepository.buscarProdutosExistentesPorSku(numerosSKUs);
    }
}
