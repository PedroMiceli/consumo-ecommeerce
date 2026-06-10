package com.consumo_ecommerce.consumo_ecommerce.model.models.produto;

import com.consumo_ecommerce.consumo_ecommerce.model.models.BaseEntity;
import com.consumo_ecommerce.consumo_ecommerce.model.models.venda.Venda;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Setter
@Getter
public class Produto extends BaseEntity {

    @Column(name = "sku")
    private String sku;

    @Column(name = "nome")
    private String nome;

    @Column(name = "custo")
    private BigDecimal custo;

    public Produto() {
    }

    public Produto(UUID id, String sku, BigDecimal custo) {
        this.setId(id);
        this.sku = sku;
        this.custo = custo;
    }

}

