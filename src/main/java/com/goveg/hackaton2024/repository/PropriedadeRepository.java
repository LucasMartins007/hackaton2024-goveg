package com.goveg.hackaton2024.repository;

import com.goveg.hackaton2024.model.entity.Propriedade;
import com.goveg.hackaton2024.model.enums.EnumStatusPedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PropriedadeRepository extends JpaRepository<Propriedade, Integer> {

    @Query(" select p from Propriedade p " +
            " inner join p.produtos pp" +
            " where pp.statusPedido = :status ")
    List<Propriedade> findAllProdutosByStatus(EnumStatusPedido status);
}
