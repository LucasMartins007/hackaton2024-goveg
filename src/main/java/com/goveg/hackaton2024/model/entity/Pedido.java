package com.goveg.hackaton2024.model.entity;

import com.goveg.hackaton2024.model.enums.EnumStatusPedido;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
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

    @Column(name = "status_pedido")
    @Enumerated(EnumType.STRING)
    private EnumStatusPedido statusPedido;

    @Column(name = "data_recebimento")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataRecebimento;

    @Column(name = "data_aceite")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataAceite;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "empresa_id", referencedColumnName = "id")
    private Empresa empresa;

    @Column(name = "preco_total")
    private BigDecimal precototal;

    @Column(name = "qtd_total")
    private BigDecimal quantidadeTotal;

    @Column (name = "observacao")
    private String observacao;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Produto> produto;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Mensagem> mensagens;

}