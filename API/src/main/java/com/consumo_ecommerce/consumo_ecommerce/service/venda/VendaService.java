package com.consumo_ecommerce.consumo_ecommerce.service.venda;

import com.consumo_ecommerce.consumo_ecommerce.model.models.venda.Venda;
import com.consumo_ecommerce.consumo_ecommerce.model.models.venda.VendaImportacaoErro;
import com.consumo_ecommerce.consumo_ecommerce.model.repositories.VendaImportacaoErroRepository;
import com.consumo_ecommerce.consumo_ecommerce.model.repositories.VendaRepository;
import com.consumo_ecommerce.consumo_ecommerce.model.repositories.projections.VendaProjection;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Service
public class VendaService implements  IVendaService {

    @Autowired
    private VendaRepository vendaRepository;

    @Autowired
    private VendaImportacaoErroRepository vendaImportacaoErroRepository;



    @Override
    @Transactional
    public void salvarVendas(List<Venda> vendas) {
        vendaRepository.saveAll(vendas);
    }

    @Override
    public List<VendaProjection> buscarVendas(LocalDateTime dataInicio, LocalDateTime dataFim){
        return vendaRepository.findByDataVendaGreaterThanEqualAndDataVendaLessThan(dataInicio,dataFim);
    }

    @Override
    public void salvarVendaImportacaoErro(List<VendaImportacaoErro> vendasImportacaoErros) {
        vendaImportacaoErroRepository.saveAll(vendasImportacaoErros);
    }

    @Override
    public Set<String> buscarNumerosVendaExistentesComErro(Set<String> numerosVenda) {
        if (numerosVenda == null || numerosVenda.isEmpty()) {
            return Set.of();
        }
        return vendaImportacaoErroRepository.buscarNumerosVendaExistentes(numerosVenda);
    }

    @Override
    public Set<String> buscarNumerosVendaExistentes(Set<String> numerosVenda){
        if (numerosVenda == null || numerosVenda.isEmpty()) {return Set.of();}
        return vendaRepository.buscarNumerosVendaExistentes(numerosVenda);
    }
}
