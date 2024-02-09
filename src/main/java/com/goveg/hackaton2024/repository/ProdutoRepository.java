package com.goveg.hackaton2024.repository;

import com.goveg.hackaton2024.model.entity.Produto;
import com.goveg.hackaton2024.model.enums.EnumStatusPedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Integer> {

    List<Produto> findAllByStatusPedido(EnumStatusPedido statusPedido);
}
