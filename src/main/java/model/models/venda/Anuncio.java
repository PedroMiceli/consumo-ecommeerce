package model.models.venda;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;
import model.models.BaseEntity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
public class Anuncio extends BaseEntity {

    @Column(name = "sku")
    private String sku;

    @Column(name = "numero_anuncio")
    private String numeroAnuncio;

    @Column(name = "canal_venda")
    private String canalVenda;

    @Column(name = "titulo_anuncio")
    private String tituloAnuncio;

    @Column(name = "variacao")
    private String variacao;

    @Column(name = "preco_unitario_venda", precision = 15, scale = 2)
    private BigDecimal precoUnitarioVenda;

    @Column(name = "tipo_anuncio")
    private String tipoAnuncio;

    @OneToMany(mappedBy = "anuncio")
    private List<Venda> vendas = new ArrayList<>();
}
