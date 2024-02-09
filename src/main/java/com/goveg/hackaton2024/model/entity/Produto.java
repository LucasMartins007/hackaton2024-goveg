package com.goveg.hackaton2024.model.entity;

import com.goveg.hackaton2024.model.enums.EnumCultura;
import com.goveg.hackaton2024.model.enums.EnumStatusPedido;
import com.goveg.hackaton2024.model.enums.EnumTipoProduto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "produto")
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "produto_id_gen")
    @SequenceGenerator(name = "produto_seq", sequenceName = "produto_sequence", allocationSize = 1)
    private Integer id;

    @Column(name = "tipo_produto")
    @Enumerated(EnumType.STRING)
    private EnumTipoProduto tipoProduto;

    @Column(name = "cultura")
    @Enumerated(EnumType.STRING)
    private EnumCultura cultura;

    @Column(name = "status_pedido")
    @Enumerated(EnumType.STRING)
    private EnumStatusPedido statusPedido;

    @Column(name = "descricao")
    private String descricao;

    @Column(name = "data_aceite")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataAceite;

    @Column(name = "data_inclusao")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataInclusao;

    @Column(name = "data_conslusao")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataConclusao;

    @Column(name = "data_entrega")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataEntrega;

    @Column(name = "valor_unitario")
    private BigDecimal valorUnitario;

    @Column(name = "is_organico")
    private boolean isOrganico;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "propriedade_id", referencedColumnName = "id")
    private Propriedade propriedade;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pedido_id", referencedColumnName = "id")
    private Pedido pedido;
}
