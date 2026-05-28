package com.consumo_ecommerce.consumo_ecommerce.application.venda;

import com.consumo_ecommerce.consumo_ecommerce.application.dtos.venda.VendaResponse;
import com.consumo_ecommerce.consumo_ecommerce.application.dtos.venda.VendaRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public interface IVendaApplication {
    void salvarVendas(List<VendaRequest> vendas);
    List<VendaResponse> buscarVendas(LocalDateTime dataInicio, LocalDateTime dataFim);
}
