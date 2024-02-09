package com.goveg.hackaton2024.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "mensagem")
public class Mensagem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "empresa_id_gen")
    @SequenceGenerator(name = "mensagem_seq", sequenceName = "empresa_sequence", allocationSize = 1)
    private Integer id;

    @Column
    private String mensagem;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pedido_id", referencedColumnName = "id")
    private Pedido pedido;

}
