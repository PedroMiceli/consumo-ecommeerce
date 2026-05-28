package com.consumo_ecommerce.consumo_ecommerce.service.venda;

import com.consumo_ecommerce.consumo_ecommerce.model.models.venda.Venda;
import com.consumo_ecommerce.consumo_ecommerce.model.repositories.projections.VendaProjection;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public interface IVendaService {
    void salvarVendas(List<Venda> vendas);
    List<VendaProjection> buscarVendas(LocalDateTime dataInicio, LocalDateTime dataFim);
}
