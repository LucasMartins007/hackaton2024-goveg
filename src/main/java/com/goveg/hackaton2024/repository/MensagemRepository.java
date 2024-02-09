package com.goveg.hackaton2024.repository;

import com.goveg.hackaton2024.model.entity.Mensagem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MensagemRepository extends JpaRepository<Mensagem, Integer> {
}
