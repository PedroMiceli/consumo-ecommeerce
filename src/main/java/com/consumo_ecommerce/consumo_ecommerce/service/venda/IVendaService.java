package com.consumo_ecommerce.consumo_ecommerce.service.venda;

import com.consumo_ecommerce.consumo_ecommerce.model.models.venda.Venda;
import com.consumo_ecommerce.consumo_ecommerce.model.models.venda.VendaImportacaoErro;
import com.consumo_ecommerce.consumo_ecommerce.model.repositories.projections.VendaProjection;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Service
public interface IVendaService {
    void salvarVendas(List<Venda> vendas);
    List<VendaProjection> buscarVendas(LocalDateTime dataInicio, LocalDateTime dataFim);
    void salvarVendaImportacaoErro(List<VendaImportacaoErro> vendasImportacaoErros);
    Set<String> buscarNumerosVendaExistentes(Set<String> numerosVenda);
}
