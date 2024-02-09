package com.goveg.hackaton2024.model.enums;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum     EnumTipoProduto {

    UVA("Uva"),

    ALHO("Alho"),

    MORANGO("Morango"),

    TOMATE("Tomate"),

    CENOURA("Cenoura"),

    BATATA("Batata"),

    ALFACE("Alface"),

    MELANCIA("Melancia");

    private final String descricao;

}