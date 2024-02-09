//package com.goveg.hackaton2024.model.entity;
//
//import jakarta.persistence.*;
//import lombok.Getter;
//import lombok.Setter;
//
//import java.math.BigDecimal;
//
///**
// * @author Jhonatan
// */
//
//@Entity
//@Getter
//@Setter
//@Table(name = "cultura")
//public class Cultura {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO, generator = "cultura_id_gen")
//    @SequenceGenerator(name = "cultura_seq", sequenceName = "cultura_sequence", allocationSize = 1)
//    private Integer id;
//
//    @Column(name = "tipo")
//    private String tipo;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "propriedade_id", referencedColumnName = "id")
//    private Propriedade propriedade;
//
//    @Column(name = "preco_unitario")
//    private BigDecimal precoUnitario;
//
//    @Column(name = "qtd_kg")
//    private BigDecimal quantidadeKg;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "pedido_id", referencedColumnName = "id")
//    private Pedido pedido;
//
//}
