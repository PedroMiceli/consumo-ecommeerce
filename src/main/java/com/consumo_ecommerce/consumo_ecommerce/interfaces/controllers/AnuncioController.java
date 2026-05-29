package com.consumo_ecommerce.consumo_ecommerce.interfaces.controllers;

import com.consumo_ecommerce.consumo_ecommerce.application.anuncio.IAnuncioApplication;
import com.consumo_ecommerce.consumo_ecommerce.application.dtos.anuncio.AnuncioRequest;
import com.consumo_ecommerce.consumo_ecommerce.application.dtos.anuncio.AnuncioResponse;
import com.consumo_ecommerce.consumo_ecommerce.application.dtos.venda.VendaRequest;
import com.consumo_ecommerce.consumo_ecommerce.application.dtos.venda.VendaResponse;
import com.consumo_ecommerce.consumo_ecommerce.application.venda.IVendaApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/anuncio")
public class AnuncioController {

    @Autowired
    private IAnuncioApplication anuncioApplication;

//    @PostMapping("/importar-xlsx")
//    public ResponseEntity<Void> importarAnunciosXlsx(@RequestParam("arquivo") MultipartFile arquivo) {
//        vendaApplication.importarAnunciosXlsx(arquivo);
//        return ResponseEntity.ok().build();
//    }

    @PostMapping("/salvar-anuncios")
    public ResponseEntity<Void> importarAnuncios(@RequestBody List<AnuncioRequest> anuncios) {
        anuncioApplication.salvarAnuncios(anuncios);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/buscar-anuncios")
    public ResponseEntity<List<AnuncioResponse>> buscarAnuncios() {
        List<AnuncioResponse> anuncios = anuncioApplication.buscarAnuncios();
        return ResponseEntity.ok(anuncios);
    }
}
