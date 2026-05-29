package com.consumo_ecommerce.consumo_ecommerce.model.models.venda;

import com.consumo_ecommerce.consumo_ecommerce.model.models.anuncio.Anuncio;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import com.consumo_ecommerce.consumo_ecommerce.model.models.BaseEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Setter
@Getter
public class Venda extends BaseEntity {

    @Column(name = "numero_venda")
    private String numeroVenda;

    @Column(name = "data_venda")
    private LocalDateTime dataVenda;

    @Column(name = "deposito")
    private String deposito;

    @Column(name = "estado")
    private String estado;

    @Column(name = "descricao_status", columnDefinition = "TEXT")
    private String descricaoStatus;

    @Column(name = "pacote_diversos_produtos")
    private Boolean pacoteDiversosProdutos;

    @Column(name = "pertence_kit")
    private Boolean pertenceKit;

    @Column(name = "unidades")
    private Integer unidades;

    @Column(name = "receita_produtos", precision = 15, scale = 2)
    private BigDecimal receitaProdutos;

    @Column(name = "receita_acrescimo_preco", precision = 15, scale = 2)
    private BigDecimal receitaAcrescimoPreco;

    @Column(name = "taxa_parcelamento_acrescimo", precision = 15, scale = 2)
    private BigDecimal taxaParcelamentoAcrescimo;

    @Column(name = "tarifa_venda_impostos", precision = 15, scale = 2)
    private BigDecimal tarifaVendaImpostos;

    @Column(name = "receita_envio", precision = 15, scale = 2)
    private BigDecimal receitaEnvio;

    @Column(name = "tarifas_envio", precision = 15, scale = 2)
    private BigDecimal tarifasEnvio;

    @Column(name = "custo_envio_medidas_peso", precision = 15, scale = 2)
    private BigDecimal custoEnvioMedidasPeso;

    @Column(name = "custo_diferencas_medidas_peso", precision = 15, scale = 2)
    private BigDecimal custoDiferencasMedidasPeso;

    @Column(name = "descontos_bonus", precision = 15, scale = 2)
    private BigDecimal descontosBonus;

    @Column(name = "cancelamentos_reembolsos", precision = 15, scale = 2)
    private BigDecimal cancelamentosReembolsos;

    @Column(name = "total", precision = 15, scale = 2)
    private BigDecimal total;

    @Column(name = "mes_faturamento_tarifas")
    private String mesFaturamentoTarifas;

    @Column(name = "pedido_compra")
    private String pedidoCompra;

    @Column(name = "venda_por_publicidade")
    private boolean vendaPorPublicidade;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "anuncio_id", nullable = false)
    private Anuncio anuncio;

    public Venda() {
    }

    public Venda(UUID id, String numeroVenda, LocalDateTime dataVenda, String deposito, String estado, String descricaoStatus, Boolean pacoteDiversosProdutos, Boolean pertenceKit, Integer unidades, BigDecimal receitaProdutos, BigDecimal receitaAcrescimoPreco, BigDecimal taxaParcelamentoAcrescimo, BigDecimal tarifaVendaImpostos, BigDecimal receitaEnvio, BigDecimal tarifasEnvio, BigDecimal custoEnvioMedidasPeso, BigDecimal custoDiferencasMedidasPeso, BigDecimal descontosBonus, BigDecimal cancelamentosReembolsos, BigDecimal total, String mesFaturamentoTarifas, String pedidoCompra, boolean vendaPorPublicidade, Anuncio anuncio) {
        this.setId(id);
        this.numeroVenda = numeroVenda;
        this.dataVenda = dataVenda;
        this.deposito = deposito;
        this.estado = estado;
        this.descricaoStatus = descricaoStatus;
        this.pacoteDiversosProdutos = pacoteDiversosProdutos;
        this.pertenceKit = pertenceKit;
        this.unidades = unidades;
        this.receitaProdutos = receitaProdutos;
        this.receitaAcrescimoPreco = receitaAcrescimoPreco;
        this.taxaParcelamentoAcrescimo = taxaParcelamentoAcrescimo;
        this.tarifaVendaImpostos = tarifaVendaImpostos;
        this.receitaEnvio = receitaEnvio;
        this.tarifasEnvio = tarifasEnvio;
        this.custoEnvioMedidasPeso = custoEnvioMedidasPeso;
        this.custoDiferencasMedidasPeso = custoDiferencasMedidasPeso;
        this.descontosBonus = descontosBonus;
        this.cancelamentosReembolsos = cancelamentosReembolsos;
        this.total = total;
        this.mesFaturamentoTarifas = mesFaturamentoTarifas;
        this.pedidoCompra = pedidoCompra;
        this.vendaPorPublicidade = vendaPorPublicidade;
        this.anuncio = anuncio;
    }
}
