package com.consumo_ecommerce.consumo_ecommerce.interfaces.controllers;

import com.consumo_ecommerce.consumo_ecommerce.application.dtos.venda.VendaRequest;
import com.consumo_ecommerce.consumo_ecommerce.application.venda.IVendaApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/venda")
public class VendaController {

    @Autowired
    private IVendaApplication vendaApplication;

    @PostMapping("/importar-xlsx")
    public ResponseEntity<Void> importarVendasXlsx(@RequestParam("arquivo") MultipartFile arquivo) {
        vendaApplication.importarVendasXlsx(arquivo);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/salvar-vendas")
    public ResponseEntity<Void> importarVendas(@RequestBody List<VendaRequest> vendas){
        vendaApplication.salvarVendas(vendas);
        return ResponseEntity.ok().build();
    }
}
