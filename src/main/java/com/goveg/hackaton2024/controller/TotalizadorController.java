package com.goveg.hackaton2024.controller;

import com.goveg.hackaton2024.model.dto.TotalDTO;
import com.goveg.hackaton2024.model.entity.Produto;
import com.goveg.hackaton2024.model.enums.EnumStatusPedido;
import com.goveg.hackaton2024.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/total")
public class TotalizadorController {

    private final ProdutoRepository produtoRepository;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public TotalDTO cadastrarPropriedade() {
        final TotalDTO totalDTO = new TotalDTO();
        long total = produtoRepository.count();
        final List<Produto> produtos = produtoRepository.findAllByStatusPedido(EnumStatusPedido.ANDAMENTO);

        totalDTO.setTotal(total);
        totalDTO.setEmAndamento(produtos.size());
        return totalDTO;
    }
}
