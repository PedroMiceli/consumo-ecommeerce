package com.consumo_ecommerce.consumo_ecommerce.service.venda;

import com.consumo_ecommerce.consumo_ecommerce.application.dtos.venda.VendaRequest;
import com.consumo_ecommerce.consumo_ecommerce.application.mapper.XlsxMapper;
import com.consumo_ecommerce.consumo_ecommerce.exceptions.CampoObrigatorioException;
import com.consumo_ecommerce.consumo_ecommerce.model.models.venda.Venda;
import com.consumo_ecommerce.consumo_ecommerce.model.repositories.VendaRepository;
import com.consumo_ecommerce.consumo_ecommerce.model.repositories.projections.VendaProjection;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class VendaService implements  IVendaService {

    @Autowired
    private VendaRepository vendaRepository;



    @Override
    @Transactional
    public void salvarVendas(List<Venda> vendas) {
        vendaRepository.saveAll(vendas);
    }

    @Override
    public List<VendaProjection> buscarVendas(LocalDateTime dataInicio, LocalDateTime dataFim){
        return vendaRepository.findByDataVendaGreaterThanEqualAndDataVendaLessThan(dataInicio,dataFim);
    }
}
