package com.goveg.hackaton2024.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author jhonatan
 */

@Entity
@Getter
@Setter
@Table(name = "empresa")
public class Empresa {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "empresa_id_gen")
    @SequenceGenerator(name = "empresa_seq", sequenceName = "empresa_sequence", allocationSize = 1)
    private Integer id;

    @Column(name = "nome")
    private String name;

    @Column(name = "cpf_cnpj")
    private String cpf_cnpj;

    @Column (name = "endereco")
    private String endereco;

    @OneToMany(mappedBy = "empresa", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Pedido> pedido;
}