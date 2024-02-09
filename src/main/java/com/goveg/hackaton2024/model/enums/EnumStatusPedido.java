package com.goveg.hackaton2024.model.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author jhonatan
 */
@Getter
@RequiredArgsConstructor
public enum EnumStatusPedido {

    ABERTO("Aberto"),

    ANDAMENTO("Andamento"),

    CANCELADO("Cancelado"),

    ENTREGUE("Entregue"),

    CONCLUIDO("Concluido");

    private final String descricao;
}