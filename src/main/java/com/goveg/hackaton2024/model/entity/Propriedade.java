package com.goveg.hackaton2024.model.entity;

import com.goveg.hackaton2024.model.dto.CulturaDTO;
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
@Table(name = "propriedade")
public class Propriedade {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "propriedade_id_gen")
    @SequenceGenerator(name = "propriedade_seq", sequenceName = "propriedade_sequence", allocationSize = 1)
    private Integer id;

    @Column (name = "cep")
    private String cep;

    @Column (name = "endereco")
    private String endereco;

    @OneToMany(mappedBy = "propriedade", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Cultura> cultura;

}