package com.goveg.hackaton2024.model.dto;


import com.goveg.hackaton2024.model.enums.EnumStatusPedido;
import com.goveg.hackaton2024.model.enums.EnumCultura;
import com.goveg.hackaton2024.model.enums.EnumTipoProduto;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author jhonatan
 */

@Setter
@Getter
public class ProdutoPedidoDTO {

    private Integer id;

    private String descricao;

    private String descricaoProduto;

    private BigDecimal valor;

    private EnumStatusPedido status;

    private EnumCultura pedidoTag;

    private EnumTipoProduto produtoTipo;

    private Date dataAceite;

    private String dataConclusao;

    private Boolean organico;

    private Integer quantidade;

    private UsuarioClienteDTO usuarioClienteDTO;

    private UsuarioFornecedorDTO usuarioFornecedorDTO;

}