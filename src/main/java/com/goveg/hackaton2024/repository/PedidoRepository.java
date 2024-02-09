package com.goveg.hackaton2024.repository;

import com.goveg.hackaton2024.model.entity.Pedido;
import com.goveg.hackaton2024.model.entity.Produto;
import com.goveg.hackaton2024.model.enums.EnumStatusPedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Integer> {

    List<Pedido> findAllByStatusPedido(EnumStatusPedido statusPedido);

    @Query("select p from Pedido p " +
            "inner join p.produto pp " +
            "where pp.id = :produto")
    Pedido findByProduto(@Param("produto") Integer produto);
}
