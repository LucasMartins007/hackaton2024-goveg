package com.goveg.hackaton2024.model.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum EnumCultura {

    VERDURA("Verdura"),

    FRUTA("Fruta"),

    LEGUME("Legume");

    private final String descricao;
}