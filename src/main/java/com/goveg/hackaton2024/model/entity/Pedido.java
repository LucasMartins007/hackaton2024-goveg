package com.goveg.hackaton2024.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author jhonatan
 */

@Entity
@Getter
@Setter
@Table(name = "pedido")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "pedido_id_gen")
    @SequenceGenerator(name = "pedido_seq", sequenceName = "pedido_sequence", allocationSize = 1)
    private Integer id;

//    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    private List<Cultura> cultura;

    @Column(name = "data_entrega")
    private LocalDateTime dataEntraga;

    @Column(name = "status")
    private String status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "empresa_id", referencedColumnName = "id")
    private Empresa empresa;

    @Column(name = "preco_total")
    private BigDecimal precototal;

    @Column(name = "qtd_total")
    private BigDecimal quantidadeTotal;

    @Column (name = "endereco_entrega")
    private String enderecoEntrega;

    @Column (name = "observacao")
    private String observacao;

}