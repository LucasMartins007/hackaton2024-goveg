package com.goveg.hackaton2024.domain.dto;

import com.goveg.hackaton2024.domain.enums.EnumProductTag;
import com.goveg.hackaton2024.domain.enums.EnumProductType;
import com.goveg.hackaton2024.domain.enums.OrderOrderStatus;
import lombok.Getter;
import lombok.Setter;

/**
 * @author jhonatan
 */

@Setter
@Getter
public class ProdutoPedidoDTO {

    private String id;

    private String descricao;

    private String descricaoProduto;

    private Number valor;

    private OrderOrderStatus status;

    private EnumProductTag pedidoTag;

    private EnumProductType produtoTipo;

    private String dataAceite;

    private String dataConclusao;

    private Boolean organico;

    private Number quantidade;


}
