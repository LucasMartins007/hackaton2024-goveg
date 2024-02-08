package com.goveg.hackaton2024.model.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Entity
@Getter
@Setter
@Table(name = "pessoa")
public class Pessoa {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "pessoa_id_gen")
    @SequenceGenerator(name = "pessoa_seq", sequenceName = "pessoa_sequence", allocationSize = 1)
    private Integer id;

    @Column(name = "nome")
    private String name;

    @OneToMany(mappedBy = "pessoa", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Propriedade> propriedade;

    @Column(name = "cpf_cnpj")
    private String cpf_cnpj;

    @Column(name = "telefone")
    private String telefone;

}