package com.goveg.hackaton2024.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;


@Getter
@Setter
public class ConfirmacaoPedidoDTO {

    private BigDecimal valorTotal;

    private Date dataRecebimento;

    private BigDecimal quantidade;
}
