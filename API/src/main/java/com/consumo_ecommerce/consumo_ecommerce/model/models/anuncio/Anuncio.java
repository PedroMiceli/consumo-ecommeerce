package com.consumo_ecommerce.consumo_ecommerce.model.models.anuncio;

import com.consumo_ecommerce.consumo_ecommerce.application.dtos.venda.VendaRequest;
import com.consumo_ecommerce.consumo_ecommerce.model.models.venda.Venda;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;
import com.consumo_ecommerce.consumo_ecommerce.model.models.BaseEntity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Setter
@Getter
public class Anuncio extends BaseEntity {

    @Column(name = "sku")
    private String sku;

    @Column(name = "numero_anuncio", unique = true, nullable = false)
    private String numeroAnuncio;

    @Column(name = "canal_venda")
    private String canalVenda;

    @Column(name = "titulo_anuncio")
    private String tituloAnuncio;

    @Column(name = "variacao")
    private String variacao;

    @Column(name = "preco_unitario_venda", precision = 15, scale = 2)
    private BigDecimal precoUnitarioVenda;

    @Column(name = "custo_produto", precision = 15, scale = 2)
    private BigDecimal custoProduto;

    @Column(name = "tipo_anuncio")
    private String tipoAnuncio;

    @OneToMany(mappedBy = "anuncio")
    private List<Venda> vendas = new ArrayList<>();

    public Anuncio() {
    }

    public Anuncio(UUID id, String sku, String numeroAnuncio, String canalVenda, String tituloAnuncio, String variacao, BigDecimal precoUnitarioVenda, String tipoAnuncio) {
        this.setId(id);
        this.sku = sku;
        this.numeroAnuncio = numeroAnuncio;
        this.canalVenda = canalVenda;
        this.tituloAnuncio = tituloAnuncio;
        this.variacao = variacao;
        this.precoUnitarioVenda = precoUnitarioVenda;
        this.tipoAnuncio = tipoAnuncio;
    }




}
