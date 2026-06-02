package com.consumo_ecommerce.consumo_ecommerce.model.models.venda;

import com.consumo_ecommerce.consumo_ecommerce.application.dtos.venda.VendaRequest;
import com.consumo_ecommerce.consumo_ecommerce.model.models.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "venda_importacao_erro")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VendaImportacaoErro extends BaseEntity {

    private String numeroVenda;

    private String numeroAnuncio;

    @Column(columnDefinition = "TEXT")
    private String descricaoErro;

    public static VendaImportacaoErro converter(VendaRequest request, String motivoErro) {
        VendaImportacaoErro erro = new VendaImportacaoErro();

        erro.setNumeroVenda(request.numeroVenda());
        erro.setNumeroAnuncio(request.numeroAnuncio());
        erro.setDescricaoErro(motivoErro);

        return erro;
    }

}
