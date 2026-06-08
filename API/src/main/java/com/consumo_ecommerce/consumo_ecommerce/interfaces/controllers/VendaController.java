package com.consumo_ecommerce.consumo_ecommerce.interfaces.controllers;

import com.consumo_ecommerce.consumo_ecommerce.application.dtos.venda.VendaRequest;
import com.consumo_ecommerce.consumo_ecommerce.application.dtos.venda.VendaResponse;
import com.consumo_ecommerce.consumo_ecommerce.application.venda.IVendaApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/venda")
public class VendaController {

    @Autowired
    private IVendaApplication vendaApplication;

    @PostMapping(value = "/importar-xlsx",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> importarVendasXlsx(@RequestParam("arquivo") MultipartFile arquivo) {
        vendaApplication.importarVendasXlsx(arquivo);
        return ResponseEntity.ok().build();
    }



    @PostMapping("/salvar-vendas")
    public ResponseEntity<Void> importarVendas(@RequestBody List<VendaRequest> vendas){
        vendaApplication.salvarVendas(vendas);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/buscar-vendas-totais")
    public ResponseEntity<List<VendaResponse>> buscarVendasTotais(@RequestParam("dataInicio") LocalDateTime dataInicio, @RequestParam("dataFim") LocalDateTime dataFim){
        List<VendaResponse> vendas = vendaApplication.buscarVendas(dataInicio,dataFim);
        return ResponseEntity.ok(vendas);
    }

    @GetMapping("/buscar-vendas")
    public ResponseEntity<List<VendaResponse>> buscarVendas(@RequestParam("dataInicio") LocalDateTime dataInicio, @RequestParam("dataFim") LocalDateTime dataFim){
        List<VendaResponse> vendas = vendaApplication.buscarVendas(dataInicio,dataFim);
        return ResponseEntity.ok(vendas);
    }
}
